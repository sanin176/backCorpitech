package com.alex.Controller;

import com.alex.Model.Customer;
import com.alex.Model.User;
import com.alex.Services.CustomerRepository;
import com.alex.Services.CustomerSeatRepository;
import com.alex.Services.UserRepository;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class Controller {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerSeatRepository customerSeatRepository;

    @RequestMapping(value = {"/checkLogin"}, method = RequestMethod.POST)
    public ResponseEntity<?> checkLogin(@RequestBody User u) throws JSONException {

        System.out.println("----------------------> " + u.toString());

        List<User> checkUser = userRepository.findUserByLogin(u.getLogin());

        System.out.println("----------------------> " + BCrypt.checkpw(u.getPasswordHash(), checkUser.get(0).getPasswordHash()));

        if (BCrypt.checkpw(u.getPasswordHash(), checkUser.get(0).getPasswordHash())) {
            userRepository.updateUserByLogin(u.getLogin(), LocalDate.now());

            JSONObject jsonObject = new JSONObject();

            jsonObject.put("name", checkUser.get(0).getName());

            return new ResponseEntity<>("" + jsonObject, HttpStatus.OK);
        } else
            return new ResponseEntity<>("You have problem with connection", HttpStatus.BAD_REQUEST);
    }


    @RequestMapping(value = {"/createCustomer"}, method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> createClient(@RequestBody Customer customer) {
        if (customer.getDateFrom().compareTo(customer.getDateTo()) < 0) {
            customer.setCreatedAt(LocalDate.now());
            customer.setUpdatedAt(LocalDate.now());
            customerRepository.save(customer);
        }
        else return new ResponseEntity<>("You have problem", HttpStatus.BAD_REQUEST);
        return ResponseEntity.ok("ok");
    }

    @RequestMapping(value = {"/customers/{id}"}, method = RequestMethod.GET)
    public @ResponseBody
    List<?> getAllCustomers(@PathVariable("id") int id) {
        return customerRepository.findAllSomeField(id *  10, 10);
    }

    @RequestMapping(value = {"/numberRecordsCustomers"}, method = RequestMethod.GET)
    public @ResponseBody
    List<?> getPageCountCustomers() {
        return customerRepository.numberRecordsCustomers();
    }

    @RequestMapping(value = {"/customer/{id}"}, method = RequestMethod.GET)
    public @ResponseBody
    Optional<Customer> getCustomerByID(@PathVariable("id") int id) {
        return customerRepository.findById(id);
    }

    @RequestMapping(value = {"/deleteCustomer/{id}"}, method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void deleteDataRowsSecuritiesPagesByID(@PathVariable("id") int id) {
        customerRepository.deleteById(id);
    }

    @RequestMapping(value = {"/putCustomer"}, method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public void putCustomer(@RequestBody Customer c) {
        c.setUpdatedAt(LocalDate.now());
        customerRepository.save(c);
    }



    @RequestMapping(value = {"/createUser"}, method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> createUser(@RequestBody User user) {
        user.setPasswordHash(BCrypt.hashpw(user.getPasswordHash(), BCrypt.gensalt(12)));
        user.setActive(false);
        user.setCreatedAt(LocalDate.now());
        user.setUpdatedAt(LocalDate.now());
        userRepository.save(user);
        return ResponseEntity.ok("ok");
    }

    @RequestMapping(value = {"/users/{id}"}, method = RequestMethod.GET)
    public @ResponseBody
    List<?> getAllUsers(@PathVariable("id") int id) {
        return userRepository.findAllSomeField(id *  10, 10);
    }

    @RequestMapping(value = {"/numberRecordsUsers"}, method = RequestMethod.GET)
    public @ResponseBody
    List<?> getPageCountUsers() {
        return userRepository.numberRecordsUsers();
    }

    @RequestMapping(value = {"/user/{id}"}, method = RequestMethod.GET)
    public @ResponseBody
    List<?> getUserByID(@PathVariable("id") int id) {
        return userRepository.findByIdSomeField(id);
    }

    @RequestMapping(value = {"/deleteUser/{id}"}, method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void deleteUserByID(@PathVariable("id") int id) {
        userRepository.deleteById(id);
    }

    @RequestMapping(value = {"/putUser"}, method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public void putUser(@RequestBody User u) {
        u.setPasswordHash(BCrypt.hashpw(u.getPasswordHash(), BCrypt.gensalt(12)));
        u.setUpdatedAt(LocalDate.now());
        userRepository.save(u);
    }




    @RequestMapping(value = {"/service/demo/"}, method = RequestMethod.GET)
    public @ResponseBody
    List<?> getDemoInfo(@RequestParam("email") String email, @RequestParam("name") String name,
                        @RequestParam("organization") String organization, @RequestParam("seatcount") int seatcount,
                        @RequestParam("worstationcount") int worstationcount, @RequestParam("note") String note) {
        return customerRepository.findDemoInfo(email, name, organization, seatcount, worstationcount, note);
    }

    @RequestMapping(value = {"/service/check/{LicenseKey}"}, method = RequestMethod.GET)
    public @ResponseBody
    List<?> getLicenseKey(@PathVariable("LicenseKey") String LicenseKey) {
        return customerRepository.findInfoByicenseKey(LicenseKey);
    }

    @RequestMapping(value = {"/service/account/{LicenseKey}/{Email}/{FIO}"}, method = RequestMethod.GET)
    public @ResponseBody
    List<?> getStatus(@PathVariable("LicenseKey") String LicenseKey,
                      @PathVariable("Email") String Email,
                      @PathVariable("FIO") String FIO) {
        return customerRepository.findStatusByInfo(LicenseKey, Email, FIO);
    }



}
