package com.proxzone.cloud.advance;

import com.proxzone.cloud.model.User;
import graphql.GraphQL;
import graphql.schema.GraphQLObjectType;
import graphql.schema.GraphQLSchema;

import java.util.Map;

import static graphql.Scalars.GraphQLString;
import static graphql.schema.GraphQLFieldDefinition.newFieldDefinition;
import static graphql.schema.GraphQLObjectType.newObject;

/**
 * @author mayanbin@proxzone.com
 * @version 1.0
 * @date 18-9-7 下午4:21
 */
public class GraphQL_object {
    public static void main(String[] args) {
        //服务端示例数据
        User user=new User();
        user.setName("mayanbin");
        user.setSex("男");
        user.setIntro("cloud-platform,GraphQL test");

        //定义GraphQL类型
        GraphQLObjectType userType = newObject()
                .name("User")
                .field(newFieldDefinition().name("name").type(GraphQLString))
                .field(newFieldDefinition().name("sex").type(GraphQLString))
                .field(newFieldDefinition().name("intro").type(GraphQLString))
                .build();

        //定义暴露给客户端的查询query api
        GraphQLObjectType queryType = newObject()
                .name("userQuery")
                .field(newFieldDefinition().type(userType).name("user").staticValue(user))
                .build();

        //构建Schema
        GraphQLSchema schema = GraphQLSchema.newSchema()
                .query(queryType)
                .build();

        //测试输出
        GraphQL graphQL = GraphQL.newGraphQL(schema).build();
        Map<String, Object> result = graphQL.execute("{user{name,sex,intro}}").getData();
        System.out.println(result);
    }
}
