package com.proxzone.cloud.advance;
import com.proxzone.cloud.model.DogInterface;
import com.proxzone.cloud.model.FishInterface;
import com.proxzone.cloud.model.IAnimal;
import graphql.GraphQL;
import graphql.TypeResolutionEnvironment;
import graphql.schema.*;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static graphql.Scalars.GraphQLInt;
import static graphql.Scalars.GraphQLString;
import static graphql.schema.GraphQLFieldDefinition.newFieldDefinition;
import static graphql.schema.GraphQLInterfaceType.newInterface;
import static graphql.schema.GraphQLObjectType.newObject;
/**
 * @author mayanbin@proxzone.com
 * @version 1.0
 * @date 18-9-7 下午4:16
 */
public class GraphQL_interface {
    //定义GraphQL类型
    private  static GraphQLInterfaceType animalInterface = newInterface()//定义接口(interface)类型
            .name("IAnimal")
            .description("动物接口")
            .field(newFieldDefinition()
                    .name("name")
                    .type(GraphQLString))
            .typeResolver(new TypeResolver() {
                @Override
                public GraphQLObjectType getType(TypeResolutionEnvironment env) {
                    if (env.getObject() instanceof DogInterface) {
                        return dogType;
                    }
                    if (env.getObject() instanceof FishInterface) {
                        return fishType;
                    }
                    return  null;
                }
            })
            .build();

    private  static GraphQLObjectType dogType = newObject()//定义Dog类型
            .name("DogInterface")
            .field(newFieldDefinition().name("name").type(GraphQLString))
            .field(newFieldDefinition().name("legs").type(GraphQLInt))
            .withInterface(animalInterface)
            .build();


    private  static GraphQLObjectType fishType = newObject()//定义Fish类型
            .name("FishInterface")
            .field(newFieldDefinition().name("name").type(GraphQLString))
            .field(newFieldDefinition().name("tailColor").type(GraphQLString))
            .withInterface(animalInterface)
            .build();

    public static void main(String[] args) {
        //服务端示例数据
        DogInterface dog=new DogInterface();
        dog.setName("dog");
        dog.setLegs(4);

        FishInterface fish=new FishInterface();
        fish.setName("fish");
        fish.setTailColor("red");

        IAnimal[] anmials={dog,fish};

        //定义暴露给客户端的查询query api
        GraphQLObjectType queryType = newObject()
                .name("animalQuery")
                .field(newFieldDefinition()
                        .type(new GraphQLList(animalInterface))
                        .name("animals")
                        .dataFetcher(environment -> {
                            return anmials;
                        }))
                .build();

        //额外的GraphQL类型
        Set<GraphQLType> types=new HashSet<>();
        types.add(dogType);
        types.add(fishType);

        //创建Schema
        GraphQLSchema schema = GraphQLSchema.newSchema()
                .query(queryType)
                .build(types);

        //测试输出
        GraphQL graphQL = GraphQL.newGraphQL(schema).build();
        Map<String, Object> result = graphQL.execute("{animals{name ... on DogInterface{legs}　... on FishInterface{tailColor}}}").getData();
        System.out.println(result);
    }


}
