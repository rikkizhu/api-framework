package restassured.apiobject;


import io.restassured.builder.ResponseBuilder;
import io.restassured.filter.Filter;
import io.restassured.filter.FilterContext;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;

/**
 * @program: restassured.apiobject.TokenFilter
 * @description:
 * @author: zhuruiqi
 * @create: 2021-06-07 10:55
 **/
public class TokenFilter implements Filter {

    @Override
    public Response filter(FilterableRequestSpecification requestSpec, FilterableResponseSpecification responseSpec, FilterContext ctx) {
        requestSpec.queryParam("access_token",TokenHelper.getAccessToken());
        Response responseOrigin = ctx.next(requestSpec,responseSpec);
        Response responseAction = new ResponseBuilder().clone(responseOrigin).build();
        return responseAction;
    }
}
