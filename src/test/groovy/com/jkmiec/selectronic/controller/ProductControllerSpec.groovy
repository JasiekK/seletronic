package com.jkmiec.selectronic.controller

import com.google.gson.Gson
import com.jkmiec.selectronic.controller.util.TestUtil
import com.jkmiec.selectronic.entity.Category
import com.jkmiec.selectronic.entity.Comment
import com.jkmiec.selectronic.entity.Parameters
import com.jkmiec.selectronic.entity.Product
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
class ProductControllerSpec extends Specification implements TestUtil {

    @Shared
    MockMvc mvc

    @Autowired
    WebApplicationContext context

    @Shared
    Gson gson = new Gson()

    @Shared
    Category categoryTV

    @Shared
    Category categoryPhone

    @Shared
    Product product

    def setup() {
        mvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build()
        categoryTV = populateCategory()
        product = populateProduct(categoryTV)
        categoryPhone = populateCategoryPhone()
    }

    def 'any request without basic auth should not be possible'() {

        when: 'try to get list of category'
        MvcResult mvcResult = mvc.perform(get('http://localhost:8080/category/' + 1L + '/products/')).andReturn()

        then: 'response status should be UNAUTHORISED - 401'
        mvcResult.response.status == 401
    }

    @WithCustomUser
    def 'create new product then update category and parameters'() {

        setup: 'crete new categories tv and phone'
        MvcResult mvcResultCategoryTV = mvc.perform(post('http://localhost:8080/categories')
                .contentType('application/json;charset=UTF-8')
                .content(gson.toJson(categoryTV)))
                .andReturn()

        MvcResult mvcResultCategoryPhone = mvc.perform(post('http://localhost:8080/categories')
                .contentType('application/json;charset=UTF-8')
                .content(gson.toJson(categoryPhone)))
                .andReturn()

        when: 'response status should be CREATED - 201'
        mvcResultCategoryTV.response.status == 201
        mvcResultCategoryPhone.response.status == 201


        and: 'get product id from response headers'
        Long categoryTvId = getIdFromUri(mvcResultCategoryTV.response.headers.get('Location').value as String)
        Long categoryPhoneId =getIdFromUri(mvcResultCategoryPhone.response.headers.get('Location').value as String)

        product.category.id = categoryTvId

        then: 'create new product'
        MvcResult mvcResultProduct = mvc.perform(post('http://localhost:8080/products')
                .contentType('application/json;charset=UTF-8')
                .content(gson.toJson(product)))
                .andReturn()

        when: 'response status for product should be CREATED - 201'
        mvcResultProduct.response.status == 201

        and: 'get product id from response headers'
        Long id = getIdFromUri(mvcResultProduct.response.headers.get('Location').value as String)

        and: 'change category and add parameters and comment'
        categoryPhone.id = categoryPhoneId
        product.setCategory(categoryPhone)
        product.getParameters().add(new Parameters(name: "name", value: "Samsung UE22H5600"))
        product.getComment().add(new Comment("test"))

        then: 'change product category'
        MvcResult mvcResultChangeCategoryProduct = mvc.perform(put('http://localhost:8080/products/' + id)
                .contentType('application/json;charset=UTF-8')
                .content(gson.toJson(product)))
                .andReturn()

        and: 'response status for product should be NO CONTENT - 204'
        mvcResultChangeCategoryProduct.response.status == 204
    }

    Product populateProduct(Category category) {
        Parameters size = new Parameters("size", "50")
        Parameters color = new Parameters("color", "black")

        Set<Parameters> parameterSet = new HashSet<>()
        parameterSet.add(size)
        parameterSet.add(color)

        Comment comment = new Comment("Good TV")
        Comment comment2 = new Comment("Bad TV")

        Set<Comment> commentSet = new HashSet<>()
        commentSet.add(comment)
        commentSet.add(comment2)

        new Product(category, parameterSet, true, commentSet)
    }

    Category populateCategory() {
        new Category( "TV")
    }

    Category populateCategoryPhone() {
        new Category( "phone")
    }

}
