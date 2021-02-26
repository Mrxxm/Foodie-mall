package com.test;

import com.kenrou.Application;
import com.test.es.pojo.Stu;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.query.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class ESTest {

    @Autowired
    private ElasticsearchTemplate esTemplate;

    /**
     * 不建议使用 ESTemplate 对索引进行管理 (创建、更新、删除索引)
     * 索引 就像是 数据库中的表，平常不会通过代码频繁进行改动
     * 只会对数据做CURD操作
     * 1.属性(FieldType)类型不灵活
     * 2.主分片和副本分片无法设置
     */

    // 插入数据 并创建索引
    @Test
    public void createIndexStu() {
        Stu stu = new Stu();
        stu.setStuId(1002L);
        stu.setName("better man");
        stu.setAge(19);
        stu.setMoney(19.8f);
        stu.setSign("i am spider man");
        stu.setDescription("I wish i am Iron Man");

        IndexQuery indexQuery = new IndexQueryBuilder().withObject(stu).build();
        esTemplate.index(indexQuery);
    }

    // 删除索引
    @Test
    public void deleteIndexStu() {

        esTemplate.deleteIndex(Stu.class);
    }

    // --------------------------------------  文档数据相关操作 --------------------------------------------

    // 更新数据
    @Test
    public void updateStuDoc() {

        Map<String, Object> sourceMap = new HashMap<>();
        sourceMap.put("sign", "I am not super man");
        sourceMap.put("money", 88.6f);

        IndexRequest indexRequest = new IndexRequest();
        indexRequest.source(sourceMap);

        UpdateQuery updateQuery = new UpdateQueryBuilder()
                                        .withClass(Stu.class)
                                        .withId("1002")
                                        .withIndexRequest(indexRequest)
                                        .build();

        // update stu set sign = 'abc' where id = 1002;

        esTemplate.update(updateQuery);
    }

    // 获取数据
    @Test
    public void getIndexStu() {

        GetQuery query = new GetQuery();
        query.setId("1002");

        Stu stu = esTemplate.queryForObject(query, Stu.class);

        System.out.println(stu.toString());
    }

    // 删除数据
    @Test
    public void deleteStu() {

        esTemplate.delete(Stu.class, "1001");
    }

    // --------------------------------------  文档数据搜索相关操作 --------------------------------------------

    // 搜索
    @Test
    public void searchStuDoc() {

        Pageable pageable = PageRequest.of(0, 10);

        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                                        .withQuery(QueryBuilders.matchQuery("description", "save man"))
                                        .withPageable(pageable)
                                        .build();

        AggregatedPage<Stu> pagedStu = esTemplate.queryForPage(searchQuery, Stu.class);

        System.out.println("检索后的总分页数目：" + pagedStu.getTotalPages());
        List<Stu> stuList = pagedStu.getContent();
        for (Stu s: stuList) {
            System.out.println(s);
        }
    }

    // 高亮
    @Test
    public void highlightStuDoc() {

        String preTag = "<font color='red'>";
        String postTag = "</font>";

        Pageable pageable = PageRequest.of(0, 10);

        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.matchQuery("description", "save man"))
                .withHighlightFields(new HighlightBuilder.Field("description")
                .preTags(preTag).postTags(postTag))
                .withPageable(pageable)
                .build();

        AggregatedPage<Stu> pagedStu = esTemplate.queryForPage(searchQuery, Stu.class);

        System.out.println("检索后的总分页数目：" + pagedStu.getTotalPages());
        List<Stu> stuList = pagedStu.getContent();
        for (Stu s: stuList) {
            System.out.println(s);
        }
    }

    // 排序
    @Test
    public void sortStuDoc() {

        SortBuilder sortBuilder = new FieldSortBuilder("money").order(SortOrder.ASC);
        SortBuilder sortBuilderAge = new FieldSortBuilder("age").order(SortOrder.ASC);

        String preTag = "<font color='red'>";
        String postTag = "</font>";

        Pageable pageable = PageRequest.of(0, 10);

        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.matchQuery("description", "save man"))
                .withHighlightFields(new HighlightBuilder.Field("description")
                        .preTags(preTag).postTags(postTag))
                .withSort(sortBuilder)
                .withSort(sortBuilderAge)
                .withPageable(pageable)
                .build();

        AggregatedPage<Stu> pagedStu = esTemplate.queryForPage(searchQuery, Stu.class);

        System.out.println("检索后的总分页数目：" + pagedStu.getTotalPages());
        List<Stu> stuList = pagedStu.getContent();
        for (Stu s: stuList) {
            System.out.println(s);
        }
    }
}
