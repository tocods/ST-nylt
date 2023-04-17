package com.yifan.bookstore.serviceimpl;


import com.yifan.bookstore.dao.CustomerDao;
import com.yifan.bookstore.entry.Customer;
import com.yifan.bookstore.repository.CustomerRepository;
import com.yifan.bookstore.service.CustomerInfoManagement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
//@SpringBootApplication
@Service
public class CustomerInfoManagementImpl implements CustomerInfoManagement {
    @Autowired
    CustomerDao customerDao;



    public String checkSession(HttpSession httpSession){
        String message = "No info received";
        if(httpSession.getAttribute("user")!=null)
            return "Succeed";
        else {
            return "Failure";
        }
    }


    //public void logout(HttpSession httpSession){
        //httpSession.setAttribute("user",null);
    //}



    public String checkLogin(String usn, String psw, HttpSession httpSession){
        Customer customer = customerDao.getCustomerByUsername(usn);
        if(customer==null){
            return "Unknown user";
        }
        else if (customer.getPassword().equals(psw)){
            if (customer.getIs_valid() == 0){
                return "Blocked user";
            }
            httpSession.setAttribute("user", usn);
            return httpSession.getAttribute("user").toString();
            //return "Succeed";
        }
        else {
            return "Failure";
        }
    }



    public String getInfo(HttpServletRequest request,String usn){
        HttpSession httpSession = request.getSession();

        Object user = httpSession.getAttribute("user");
       // String username ="ldx";
        if(customerDao.getCustomerByUsername(usn)==null){return"Not logged in";}
        else {
            Customer customer = customerDao.getCustomerByUsername(usn);
            return(
                    "{\"phone\" : \"" + customer.getPhone() +
                            "\", \"address\" : \"" + customer.getAddress() +
                            "\", \"email\" : \"" +customer.getEmail() +
                            "\", \"name\" : \""+ customer.getName() +"\"}"
            );
        }
    }


    public String updateProfile(HttpSession httpSession,String usn, String phone, String email, String name, String address){
        Object user = httpSession.getAttribute("user");
        //if(user==null)
           // return "Not logged in";
        Customer customer = customerDao.getCustomerByUsername(usn);
        if (customer==null)
            return "Not logged in";
        customer.setAddress(address);
        customer.setEmail(email);
        customer.setName(name);
        customer.setPhone(phone);
        customerDao.save(customer);
        return "Succeed";
    }


    public String signUp(HttpServletRequest request, String username, String password,
                         String phone, String email, String address, String realname)
    {
        Customer customer = customerDao.getCustomerByUsername(username);
        if (customer!=null)
            return "Username used";
        Customer newCustomer = new Customer();
        newCustomer.setAddress(address);
        newCustomer.setEmail(email);
        newCustomer.setName(realname);
        newCustomer.setPassword(password);
        newCustomer.setPhone(phone);
        newCustomer.setUsername(username);
        newCustomer.setIs_valid(1);
        customerDao.save(newCustomer);
       HttpSession httpSession = request.getSession();
        httpSession.setAttribute("user", username);
        //if(httpSession.getAttribute("user")==null)return"Username used";
        return "Succeed";
    }

}
