package com.yifan.bookstore.serviceimpl;


import com.yifan.bookstore.WebSocketServer;
import com.yifan.bookstore.dao.*;
import com.yifan.bookstore.entry.*;
import com.yifan.bookstore.repository.*;
import com.yifan.bookstore.service.IndentManagement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

import java.util.List;
import java.util.Calendar;
import java.util.Objects;

@RestController
//@SpringBootApplication
@Service
public class IndentManagementImpl implements IndentManagement {
    @Autowired
    CustomerDao customerDao;

    @Autowired
    BookDao bookDao;

    @Autowired
    OrderFormDao orderFormDao;

    @Autowired
    IndentItemsDao indentItemsDao;

    @Autowired
    IndentDao indentDao;
@Autowired
    WebSocketServer ws;
    @Transactional(rollbackFor = {Exception.class})
    public String createIndent(String usn){
       // Object usn = httpSession.getAttribute("user");
        //if(usn==null)
           // return "Not logged in";
        String username=usn;
        StringBuffer buf = new StringBuffer("[");
        List<OrderForm> orders = orderFormDao.getOrderFormsByUsername(username);
        int len = orders.size();
        if (len==0){
            ws.sendMessageToUser(usn,"Done");
            return "No order to submit";
        }
        Indent new_indent = new Indent();
        //get date
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH) + 1;
        int date = c.get(Calendar.DATE);
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        int second = c.get(Calendar.SECOND);
        String create_time = year + "-" +
                ((month>=10)?month:("0"+month)) + "-" +
                (date>=10?date:("0"+date)) + "T" +
                (hour>=10?hour:("0"+hour)) + ":" +
                (minute>=10?minute:("0"+minute)) + ":" +
                (second>=10?second:("0"+second));
        new_indent.setCreateTime(create_time);
        indentDao.save(new_indent);//相当于作业要求的order
        //int result=10/0;
        for (int i=0; i<len; i++){
            OrderForm order = orders.get(i);
            //try{
            Book book = bookDao.getBookByBookId(order.getBook_id());
           // book.setSales(book.getSales()+order.getAmount());
            book.setInventory(book.getInventory()-order.getAmount());
            IndentItems new_item = new IndentItems();
            new_item.setAmount(order.getAmount());
            new_item.setBookId(book.getId());
            new_item.setBookname(book.getName());
            new_item.setBookauthor(book.getAuthor());
            new_item.setBooktype(book.getType());
            new_item.setBookprice(book.getPrice());
            new_item.setIndentId(new_indent.getIndentId());
            new_item.setUsername(username);
            new_item.setIndentId(new_indent.getIndentId());
            orderFormDao.delete(order);
            try{
            indentItemsDao.save(new_item);}
            catch (Exception e){
                e.printStackTrace();
            }//相当于作业中要求的orderitem
            /*catch(Exception e){
                //TransactionAspectSupport.currentTransactionStatus()
                        //.setRollbackOnly();
                throw e;
            }*/
            buf.append(
                    "{\"Book_name\" : \"" + book.getName() +
                            "\", \"Author\" : \"" + book.getAuthor() +
                            "\", \"OrderID\" : \"" + new_item.getIndentId() +
                            "\", \"Time\" : \"" + create_time +
                            "\", \"Price\" : \"" + new_item.getBookprice() * new_item.getAmount() +
                            "\", \"Amount\" : \"" + new_item.getAmount() + "\"},");

        }
        if (buf.toString().equals("["))
            return "[]";
        buf.deleteCharAt(buf.length()-1);
        buf.append("]");
        // int result=10/0;
        ws.sendMessageToUser(usn, buf.toString());
        return "Success";
    }


    public String fetchIndents(HttpSession httpSession, String usn,String book_filter, String author_filter, String time_filter){
        //Object usn = httpSession.getAttribute("user");
       // if(usn==null)
            //return "Not logged in";
        String username=usn;
        List<IndentItems> indentItemsList = indentItemsDao.getByUsername(username);
        int len = indentItemsList.size();
        if (book_filter==null)
            book_filter = "";
        if (author_filter == null)
            author_filter = "";
        if (time_filter==null)
            time_filter = "";


        StringBuffer buf = new StringBuffer("[");
        for (int i=0; i<len; i++) {
            IndentItems indentItem = indentItemsList.get(i);
            int id = indentItem.getIndentId();

            String time = indentDao.getIndentByIndentId(indentItem.getIndentId()).getCreateTime();
            Book book = bookDao.getBookByBookId(indentItem.getBookId());
                String name = indentItem.getBookname();
                String author = indentItem.getBookauthor();

                if (!name.toLowerCase().contains(book_filter.toLowerCase())) {
                    continue;
                }
                if (!author.toLowerCase().contains(author_filter.toLowerCase())) {
                    continue;
                }
                if (!time.startsWith(time_filter)) {
                    continue;
                }
                buf.append(
                        "{\"Book_name\" : \"" + name +
                                "\", \"Author\" : \"" + indentItem.getBookauthor() +
                                "\", \"OrderID\" : \"" + indentItem.getIndentId() +
                                "\", \"Time\" : \"" + time +
                                "\", \"Price\" : \"" + indentItem.getBookprice() * indentItem.getAmount() +
                                "\", \"Amount\" : \"" + indentItem.getAmount() + "\"},");

        }
        if (buf.toString().equals("["))
            return "[]";
        buf.deleteCharAt(buf.length()-1);
        buf.append("]");
        return buf.toString();
    }

    @Override
    public String statIdents(HttpSession httpSession, String usn) {
        List<IndentItems> indentItemsList = indentItemsDao.getAll();
        StringBuffer buf = new StringBuffer("[");
        for (IndentItems item : indentItemsList) {
            Book book = bookDao.getBookByBookId(item.getBookId());
            if (Objects.equals(item.getUsername(), usn)) {
                buf.append(
                        "{\"OrderID\" : \"" + item.getIndentId() +
                                "\", \"Name\" : \"" + item.getBookname() +
                                "\", \"Category\" : \"" + item.getBooktype() +
                                "\", \"Amount\" : \"" + item.getAmount() +
                                "\", \"Author\" : \"" + item.getBookauthor() +
                                "\", \"Price\" : \"" + item.getAmount() * item.getBookprice() +
                                "\", \"Time\" : \"" + indentDao.getIndentByIndentId(item.getIndentId()).getCreateTime() + "\"}");
                buf.append(',');
            }
        }
        buf.deleteCharAt(buf.length() - 1);
        buf.append("]");
        return buf.toString();
    }
}
