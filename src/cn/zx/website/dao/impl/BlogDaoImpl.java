package cn.zx.website.dao.impl;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import cn.zx.website.dao.BlogDao;
import cn.zx.website.db.QueryHelper;
import cn.zx.website.domain.Blog;
import cn.zx.website.servlet.SearchBlogServlet;
import cn.zx.website.util.Constants;

public class BlogDaoImpl implements BlogDao {
	private final static Log log = LogFactory.getLog(BlogDaoImpl.class);

	@Override
	public void create(Blog blog) {
		String sql = "INSERT INTO blog(createDate,content) VALUES(?,?)";
		QueryHelper.update(sql, new Timestamp(new Date().getTime()), blog.getContent());
	}

	public List<Blog> findAllBlogs() {
		String sql = "SELECT * FROM blog";
		return QueryHelper.query(Blog.class, sql, null);
	}

	public List<Blog> findBlogs(int page) {
		String sql = "SELECT * FROM blog ORDER BY createDate DESC";
		;
		return QueryHelper.query_slice(Blog.class, sql, page, Constants.BLOG_PAGE_COUNT, null);
	}

	@Override
	public List<Blog> searchBlogs(String keyword) {
		List<Blog> blogs = new ArrayList<>();
		try {
			Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_CURRENT);
			Directory directory = FSDirectory.open(new File(Constants.INDEX_DIRECTORY_OF_BLOG));
			DirectoryReader ireader = DirectoryReader.open(directory);
			IndexSearcher isearcher = new IndexSearcher(ireader);
			QueryParser parser = new QueryParser(Version.LUCENE_CURRENT, "content", analyzer);
			Query query = parser.parse(keyword);
			ScoreDoc[] hits = isearcher.search(query, null, 1000).scoreDocs;
			log.info("hits.length --> " + hits.length);

			for (int i = 0; i < hits.length; i++) {
				Document hitDoc = isearcher.doc(hits[i].doc);
				Blog blog = new Blog(Timestamp.valueOf(hitDoc.get("createDate")), hitDoc.get("content"));
				log.info("blog searched --> " + blog);
				blogs.add(blog);
			}
			ireader.close();
			directory.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return blogs;
	}
}