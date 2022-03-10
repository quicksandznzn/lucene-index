package org.quicksandzn.lucene.index;

import java.nio.file.Paths;
import java.util.List;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexableField;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

/**
 *
 * @author quicksand - 2022/3/10
 */
public class LuceneIndexTest {
    public static void main(String[] args) throws Exception {
        Directory directory = FSDirectory.open(Paths.get("/Users/zn/Downloads/test_index/"));
        Analyzer analyzer = new StandardAnalyzer();
        indexWriter(directory, analyzer);
        indexReader(directory, analyzer);
    }

    public static void indexWriter(Directory directory, Analyzer analyzer) throws Exception {
        IndexWriter indexWriter = new IndexWriter(directory, new IndexWriterConfig(analyzer));
        for (int i = 0; i < 10; i++) {
            Document document = new Document();
            document.add(new StringField("name", "小猪" + i, Field.Store.YES));
            document.add(new StringField("id", String.valueOf(i), Field.Store.YES));
            indexWriter.addDocument(document);
            indexWriter.commit();
        }
    }

    public static void indexReader(Directory directory, Analyzer analyzer) throws Exception {
        IndexReader reader = DirectoryReader.open(directory);
        System.out.printf("doc maxDoc:%s, numDoc:%s%n", reader.maxDoc(), reader.numDocs());
        IndexSearcher indexSearcher = new IndexSearcher(reader);
        QueryParser queryParser = new QueryParser("DOC_ID", analyzer);
        Query query = queryParser.parse("*:*");
        TopDocs hits = indexSearcher.search(query, 210);
        System.out.printf("totalHits %s%n", hits.totalHits);
        for (ScoreDoc scoreDoc : hits.scoreDocs) {
            Document doc = indexSearcher.doc(scoreDoc.doc);
            List<IndexableField> list = doc.getFields();
            for (IndexableField indexableField : list) {
                System.out.printf("fieldName:%s,fieldValue:%s%n", indexableField.name(), doc.get(indexableField.name()));
            }
        }
        reader.close();
    }
}
