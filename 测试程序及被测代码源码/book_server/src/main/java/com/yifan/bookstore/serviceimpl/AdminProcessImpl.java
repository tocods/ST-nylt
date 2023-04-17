package com.yifan.bookstore.serviceimpl;


import com.yifan.bookstore.dao.*;
import com.yifan.bookstore.entry.*;
import com.yifan.bookstore.repository.*;
import com.yifan.bookstore.service.AdminProcess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpSession;

import java.util.List;



//@SpringBootApplication
@Service
public class AdminProcessImpl implements AdminProcess {
    @Autowired
    CustomerDao customerDao;

    @Autowired
    BookDao bookDao;

    @Autowired
    OrderFormDao orderFormDao;

    @Autowired
    IndentDao indentDao;

    @Autowired
    IndentItemsDao indentItemsDao;


    public String adminGetBook(HttpSession httpSession,String usn){
        //Object user = httpSession.getAttribute("user");
       // if(!Objects.equals(usn, "admin")){
        //if(customerRepository.getCustomerByUsername(usn).getIsAdmin()==0){
        //   return "Not admin";
       // }}
        List<Book> bookList = bookDao.getAll();
        StringBuffer buf = new StringBuffer("[");
        for (Book book:bookList){
            buf.append(
                    "{\"ID\" : \"" + book.getId() +
                            "\", \"Name\" : \"" + book.getName() +
                            "\", \"Author\" : \"" +book.getAuthor() +
                            "\", \"Price\" : \"" +book.getPrice() +
                            "\", \"Type\" : \"" +book.getType() +
                            "\", \"Inventory\" : \"" +book.getInventory() +
                            "\", \"Isbn\" : \"" +book.getIsbn() +
                            "\", \"Image\" : \""+ book.getImage() +"\"}");
            buf.append(',');
        }
        buf.deleteCharAt(buf.length()-1);
        buf.append("]");
        return buf.toString();
    }


    public String modifyBook(HttpSession httpSession,String usn,
                             int book_id, String name,
                             String author, int isbn,
                             int inventory, float price,
                             String image, String type,String description)
    {
        try {
           // Object user = customerRepository.getCustomerByUsername(usn);
           // if(user==null || customerRepository.getCustomerByUsername(usn).getIsAdmin()==0){
           //     return "Not admin";
          //  }
            if(inventory<0 || price<=0){
                return "Invalid inventory or price";
            }
            Book book = bookDao.getBookByBookId(book_id);
            if (book==null)
                book = new Book();
            book.setId(book_id);
            book.setInventory(inventory);
            book.setAuthor(author);
            book.setName(name);
            book.setType(type);
            book.setImage(image);
            book.setPrice((int) ( price));
            book.setIsbn(isbn);
            book.setDescription(description);
            bookDao.save(book);
            return "Success";
        }
        catch (Exception e){
            return e.toString();
        }
    }


    public String modifyBook(HttpSession httpSession, int book_id)
    {
        try {
            Book book = bookDao.getBookByBookId(book_id);
            if (book==null)
                return "Nothing to delete";
            bookDao.delete(book);
            return "Success";
        }
        catch (Exception e){
            return e.toString();
        }
    }


    public String getUser(HttpSession httpSession,String usn){

        List<Customer> customers = customerDao.getAll();
        StringBuffer buf = new StringBuffer("[");
        for (Customer customer:customers){
            buf.append(
                    "{\"Username\" : \"" + customer.getUsername() +
                            "\", \"Name\" : \"" + customer.getName() +
                            "\", \"Email\" : \"" +customer.getEmail() +
                            "\", \"Phone\" : \"" +customer.getPhone() +
                            "\", \"isValid\" : \"" +customer.getIs_valid() +
                            "\", \"Address\" : \""+ customer.getAddress() +"\"}");
            buf.append(',');
        }
        buf.deleteCharAt(buf.length()-1);
        buf.append("]");
        return buf.toString();
    }


    public String modifyUser(HttpSession httpSession,
                             String username, int isValid)
    {
        try {
            Customer customer = customerDao.getCustomerByUsername(username);
            if (customer==null)
                return "No such user found.";
            customer.setIs_valid(isValid);
            customerDao.save(customer);
            return "Success";
        }
        catch (Exception e){
            return e.toString();
        }
    }



    public String modifyUser(HttpSession httpSession,String usn)
    {
        try {
                List<IndentItems> indentItemsList = indentItemsDao.getAll();
                StringBuffer buf = new StringBuffer("[");
                for (IndentItems item : indentItemsList) {
                    Book book = bookDao.getBookByBookId(item.getBookId());
                        buf.append(
                                "{\"OrderID\" : \"" + item.getIndentId() +
                                        "\", \"Name\" : \"" + item.getBookname() +
                                        "\", \"Username\" : \"" + item.getUsername() +
                                        "\", \"Category\" : \"" + item.getBooktype() +
                                        "\", \"Amount\" : \"" + item.getAmount() +
                                        "\", \"Author\" : \"" + item.getBookauthor() +
                                        "\", \"Price\" : \"" + item.getAmount() * book.getPrice() +
                                        "\", \"Time\" : \"" + indentDao.getIndentByIndentId(item.getIndentId()).getCreateTime() + "\"}");
                        buf.append(',');

                }
                buf.deleteCharAt(buf.length() - 1);
                buf.append("]");
                return buf.toString();
            }
        catch (Exception e){
            return e.toString();
        }
    }

}
