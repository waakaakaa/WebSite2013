package cn.zx.website.dao.impl;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.LockObtainFailedException;
import org.apache.lucene.util.Version;
import org.wltea.analyzer.lucene.IKAnalyzer;

import cn.zx.website.dao.BlogDao;
import cn.zx.website.db.QueryHelper;
import cn.zx.website.domain.Blog;
import cn.zx.website.util.Constants;

public class BlogDaoImpl implements BlogDao {
	// Lucene 索引
	public static final String INDEX_PATH = "/home/pi/index/blog";
	public static final String FIELD_DATE = "date";
	public static final String FIELD_CONTENT = "content";
	public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");

	@Override
	public void create(Blog blog) {
		String sql = "INSERT INTO blog(createDate,content) VALUES(?,?)";
		QueryHelper.update(sql, new Timestamp(new Date().getTime()),
				blog.getContent());
	}

	public List<Blog> findAllBlogs() {
		String sql = "SELECT * FROM blog";
		return QueryHelper.query(Blog.class, sql, (Object[]) null);
	}

	public List<Blog> findBlogs(int page) {
		String sql = "SELECT * FROM blog ORDER BY createDate DESC";
		return QueryHelper.query_slice(Blog.class, sql, page,
				Constants.BLOG_PAGE_COUNT, (Object[]) null);
	}

	@Override
	public List<Blog> searchBlogs(String keyword) {
		List<Blog> list = new ArrayList<>();
		// 实例化IKAnalyzer分词器
		Analyzer analyzer = new IKAnalyzer();

		Directory directory = null;
		IndexReader ireader = null;
		IndexSearcher isearcher = null;
		try {
			// 建立索引对象
			directory = FSDirectory.open(new File(INDEX_PATH));
			// 实例化搜索器
			ireader = IndexReader.open(directory);
			isearcher = new IndexSearcher(ireader);

			// 使用QueryParser查询分析器构造Query对象
			QueryParser qp = new QueryParser(Version.LUCENE_36, FIELD_CONTENT,
					analyzer);
			qp.setDefaultOperator(QueryParser.AND_OPERATOR);
			Query query = qp.parse(keyword);

			// 搜索相似度最高的5条记录
			TopDocs topDocs = isearcher.search(query, 1000);
			System.out.println("命中：" + topDocs.totalHits);
			// 输出结果
			ScoreDoc[] scoreDocs = topDocs.scoreDocs;
			for (int i = 0; i < topDocs.totalHits; i++) {
				Document targetDoc = isearcher.doc(scoreDocs[i].doc);
				Blog blog = new Blog(Timestamp.valueOf(targetDoc
						.get(FIELD_DATE)), targetDoc.get(FIELD_CONTENT));
				list.add(blog);
			}
			isearcher.close();
		} catch (CorruptIndexException e) {
			e.printStackTrace();
		} catch (LockObtainFailedException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} finally {
			if (ireader != null) {
				try {
					ireader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (directory != null) {
				try {
					directory.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return list;
	}

}