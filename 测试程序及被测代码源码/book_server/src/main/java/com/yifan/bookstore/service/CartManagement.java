package com.yifan.bookstore.service;

import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import com.yifan.bookstore.dao.BookDao;
import com.yifan.bookstore.dao.CustomerDao;
import com.yifan.bookstore.dao.OrderFormDao;
import com.yifan.bookstore.entry.*;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;


@Service
public class CartManagement {
     @Autowired
    CustomerDao customerDao;

@Autowired
    BookDao bookDao;

@Autowired
    OrderFormDao orderFormDao;
public String getBooklist()throws IOException, SolrServerException {
        List<Book> bookList = bookDao.getAll();
        StringBuffer buf = new StringBuffer("[");
        for (Book book:bookList){
        int id=book.getId();
        buf.append(
        "{\"_id\" : \"" + id +
        "\", \"Description\":\""+ bookDao.findBookdescriptionById(id) + "\"}"
        );
        buf.append(',');

        }
        buf.deleteCharAt(buf.length()-1);
        buf.append("]");

        return buf.toString();
        }


/*public String searchBook(String key)throws IOException, SolrServerException{
final String solrUrl = "http://localhost:8983/solr/book2";
final SolrClient client= new HttpSolrClient.Builder(solrUrl)
        .withConnectionTimeout(10000)
        .withSocketTimeout(60000)
        .build();
final Map<String, String> queryParamMap = new HashMap<String, String>();
        queryParamMap.put("q", "description:"+key);
        queryParamMap.put("fl", "id,name,author,description");
        queryParamMap.put("sort", "id asc");
        MapSolrParams queryParams = new MapSolrParams(queryParamMap);
final QueryResponse response = client.query( queryParams);
final SolrDocumentList documents = response.getResults();
        System.out.println("Found " + documents.getNumFound() + " documents");
        StringBuffer buf = new StringBuffer("[");
        for (SolrDocument document : documents) {
final String id = (String) document.getFirstValue("id");
final String name = (String) document.getFirstValue("name");
final String author =(String)document.getFirstValue("author");
final String description=(String)document.getFirstValue("description");
        buf.append(
        "{\"ID\" : \"" + id +
        "\", \"Name\" : \"" + name +
        "\", \"Author\" : \"" +author +
        "\", \"Description\":\""+description + "\"}"
        );
        buf.append(',');
        System.out.println("id: " + id + "; name: " + name);
        }
        buf.deleteCharAt(buf.length()-1);
        buf.append("]");

        return buf.toString();
        }

public String getBookByTag(String name) {

        /*List<Book> b =bookDao.getBookByTag(name);
        int len=b.size();
        StringBuffer buf = new StringBuffer("[");
        for (Book book:b){
            buf.append(
                    "{\"bookname\" : \"" + book.getName() +
                            "\", \"type\":\""+ book.getType() + "\"}"
            );
            buf.append(',');

        }
        buf.deleteCharAt(buf.length()-1);
        buf.append("]");
        if(name.equals("s"))
        return "s";
        else
        return "n";
        //return buf.toString();
        }*/

public String addCart( String usn,Integer book_id){
        String username =usn;
        if(usn==null)return "Not logged in";

        else {
                List<OrderForm> orders = orderFormDao.getOrderFormsByUsernameAndBook_id(username, book_id);
        if (orders.size() == 0) {
        // a new one
        OrderForm new_order = new OrderForm();
        new_order.setAmount(1);
        new_order.setUsername(username);
        new_order.setBook_id(book_id);
        orderFormDao.save(new_order);
        return "Succeed";
        }
        else {
        OrderForm order = orders.get(0);
        order.setAmount(order.getAmount() + 1);
        orderFormDao.save(order);
        return "Succeed";
        }
        }

        }


public String fetchCart(String usn) {
        if(usn==null) {
                return "Not logged in";
        }
        String username = usn;
        List<OrderForm> orders = orderFormDao.getOrderFormsByUsername(username);
        int len = orders.size();
        StringBuffer buf = new StringBuffer("[");
        for (int i = 0; i < len; i++) {
        OrderForm order = orders.get(i);
        Book book = bookDao.getBookByBookId(order.getBook_id());
        if(order.getAmount()>0){
        buf.append(
        "{\"Book_name\" : \"" + book.getName() +
        "\", \"Order_id\" : \"" + order.getOrderId() +
        "\", \"Book_id\" : \"" + book.getId() +
        "\", \"Author\" : \"" + book.getAuthor() +
        "\", \"Price\" : \"" + book.getPrice() * order.getAmount() +
        "\", \"Amount\" : \"" + order.getAmount() + "\"}");
        buf.append(i == len - 1 ? "]" : ",");}
        }
        return buf.toString();
        }


public String changeAmount(int order_id, int new_amount){
        if(new_amount<=0){
        OrderForm order = orderFormDao.getOrderFormsByOrderId(order_id);
       // orderFormDao.delete(order);
        return "Amount cannot be less than zero";
        }
        else{
        OrderForm order = orderFormDao.getOrderFormsByOrderId(order_id);
        if (new_amount >  bookDao.getBookByBookId(order.getBook_id()).getInventory())
        return "No enough books";
        order.setAmount(new_amount);
        orderFormDao.save(order);
        return "Succeed";
        }
        }





        }
