package com.proxzone.cloud.advance;
import com.proxzone.cloud.model.DogUnion;
import com.proxzone.cloud.model.FishUnion;
import graphql.GraphQL;
import graphql.schema.GraphQLList;
import graphql.schema.GraphQLObjectType;
import graphql.schema.GraphQLSchema;
import graphql.schema.GraphQLUnionType;

import java.util.Map;

import static graphql.Scalars.GraphQLInt;
import static graphql.Scalars.GraphQLString;
import static graphql.schema.GraphQLFieldDefinition.newFieldDefinition;
import static graphql.schema.GraphQLObjectType.newObject;
import static graphql.schema.GraphQLUnionType.newUnionType;

/**
 * @author mayanbin@proxzone.com
 * @version 1.0
 * @date 18-9-7 下午4:29
 */
public class GraphQL_union {
    public static void main(String[] args) {
        //服务端示例数据
        DogUnion dog=new DogUnion();
        dog.setName("dog");
        dog.setLegs(4);

        FishUnion fish=new FishUnion();
        fish.setName("fish");
        fish.setTailColor("red");

        Object[] anmials={dog,fish};

        //定义GraphQL类型
        GraphQLObjectType dogType = newObject()//定义Dog类型
                .name("DogUnion")
                .field(newFieldDefinition().name("name").type(GraphQLString))
                .field(newFieldDefinition().name("legs").type(GraphQLInt))
                .build();

        GraphQLObjectType fishType = newObject()//定义Fish类型
                .name("FishUnion")
                .field(newFieldDefinition().name("name").type(GraphQLString))
                .field(newFieldDefinition().name("tailColor").type(GraphQLString))
                .build();

        GraphQLUnionType animalUnion = newUnionType()//定义联合类型(union)
                .name("IAnimal")
                .possibleType(dogType)
                .possibleType(fishType)
                .description("动物联合")
                .typeResolver(env -> {
                    if(env.getObject() instanceof DogUnion){
                        return dogType;
                    }if(env.getObject() instanceof FishUnion){
                        return fishType;
                    }
                    return  null;
                })
                .build();
        
        //定义暴露给客户端的查询query api
        GraphQLObjectType queryType = newObject()
                .name("animalQuery")
                .field(newFieldDefinition()
                        .type(new GraphQLList(animalUnion))
                        .name("animals")
                        .dataFetcher(evn -> {
                            return anmials;
                        }))
                .build();

        //创建Schema
        GraphQLSchema schema = GraphQLSchema.newSchema()
                .query(queryType)
                .build();

        //测试输出
        GraphQL graphQL = GraphQL.newGraphQL(schema).build();
        //查询动物列表
        Map<String, Object> result = graphQL.execute("{animals{... on DogUnion{name,legs} ... on FishUnion{name,tailColor}}}").getData();
        System.out.println(result);
    }

}
