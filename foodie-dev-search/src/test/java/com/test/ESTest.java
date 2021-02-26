package com.test;

import com.kenrou.Application;
import com.test.es.pojo.Stu;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class ESTest {

    @Autowired
    private ElasticsearchTemplate esTemplate;

    /**
     * 不建议使用 ESTemplate 对索引进行管理 (创建、更新、删除索引)
     * 索引 就像是 数据库中的表，平常不会通过代码频繁进行改动
     * 只会对数据做CURD操作
     */

    @Test
    public void createIndexStu() {
        Stu stu = new Stu();
        stu.setStuId(1001L);
        stu.setName("better man");
        stu.setAge(18);
        stu.setMoney(18.8f);
        stu.setSign("i am spider man");
        stu.setDescription("I wish i am Iron Man");

        IndexQuery indexQuery = new IndexQueryBuilder().withObject(stu).build();
        esTemplate.index(indexQuery);
    }

    @Test
    public void deleteIndexStu() {

        esTemplate.deleteIndex(Stu.class);
    }
}
