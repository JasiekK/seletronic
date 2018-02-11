package com.jkmiec.selectronic.controller

import com.google.gson.Gson
import com.jkmiec.selectronic.controller.util.TestUtil
import com.jkmiec.selectronic.entity.Category
import com.jkmiec.selectronic.service.annotation.WithCustomUser
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.MvcResult
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import spock.lang.Shared
import spock.lang.Specification

import javax.transaction.Transactional

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*

@SpringBootTest
@Transactional
class CategoryControllerSpec extends Specification implements TestUtil {

    @Shared
    MockMvc mvc

    @Autowired
    WebApplicationContext context

    @Shared
    Gson gson = new Gson()

    @Shared
    Category category

    def setup() {
        mvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build()
        category = populateCategory()
    }

    def 'any request without basic auth should not be possible'() {

        when: 'try to get list of category'
        MvcResult mvcResult = mvc.perform(get('http://localhost:8080/categories')).andReturn()

        then: 'response status should be UNAUTHORISED - 401'
        mvcResult.response.status == 401
    }

    @WithCustomUser
    def 'should return empty list of category'() {

        when: 'get list of category'
        MvcResult mvcResult = mvc.perform(get('http://localhost:8080/categories')).andReturn()

        then: 'response status should be OK - 200'
        mvcResult.response.status == 200

        and: 'response content should be empty'
        mvcResult.response.getContentAsString() == [] as String
    }

    @WithCustomUser
    def 'create category and try to get by id'() {

        setup: 'create new category'
        MvcResult mvcResult = mvc.perform(post('http://localhost:8080/categories')
                .contentType('application/json;charset=UTF-8')
                .content(gson.toJson(category)))
                .andReturn()

        when: 'response status should be CREATED - 201'
        mvcResult.response.status == 201

        and: 'get category id from response headers'
        Long id = getIdFromUri(mvcResult.response.headers.get('Location').value as String)

        then: 'try get category by id GET /categories{categoriesId}'
        MvcResult resultById = mvc.perform(get('http://localhost:8080/categories/' + id)).andReturn()

        then: 'response status should be - 200'
        resultById.response.status == 200

        and: 'ids should be equal'
        gson.fromJson(resultById.response.getContentAsString(), Category).id == id
    }

    Category populateCategory() {
        new Category("TV")
    }
}
