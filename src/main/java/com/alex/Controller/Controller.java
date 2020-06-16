package com.alex.Controller;

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

import java.time.LocalDateTime;
import java.util.List;

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

        List<User> checkUser = userRepository.findUserByLogin(u.getLogin());

        if(BCrypt.checkpw(u.getPasswordHash(), checkUser.get(0).getPasswordHash())){
            userRepository.updateUserByLogin(u.getLogin(), LocalDateTime.now());

            JSONObject jsonObject = new JSONObject();

            jsonObject.put("name", checkUser.get(0).getName());

            return new ResponseEntity<>("" + jsonObject, HttpStatus.OK);
        }
        else
            return new ResponseEntity<>("You have problem with connection", HttpStatus.BAD_REQUEST);

    }

//    @RequestMapping(value = {"/createSecuritiesPapers"}, method = RequestMethod.POST)
//    @ResponseStatus(HttpStatus.CREATED)
//    public ResponseEntity<String> createSecuritiesPage(@RequestBody DataRowsSecuritiesPages d) {
//        if(d.getName().matches("[А-Яа-я0-9 ]+"))
//            dataRowsSecuritiesPagesRepository.save(d);
//        return ResponseEntity.ok("valid");
//    }
//
//    @RequestMapping(value = {"/securitiesPapers"}, method = RequestMethod.GET)
//    public @ResponseBody
//    Iterable<DataRowsSecuritiesPages> getAllSecuritiesPages() {
//        return dataRowsSecuritiesPagesRepository.findAll();
//    }
//
//    @RequestMapping(value = {"/securitiesPaper/{id}"}, method = RequestMethod.GET)
//    public @ResponseBody
//    Optional<DataRowsSecuritiesPages> getDataRowsSecuritiesPagesByID(@PathVariable("id") int id) {
//        return dataRowsSecuritiesPagesRepository.findById(id);
//    }
//
//    @RequestMapping(value = {"/deleteSecuritiesPaper/{id}"}, method = RequestMethod.DELETE)
//    @ResponseStatus(HttpStatus.OK)
//    public void deleteDataRowsSecuritiesPagesByID(@PathVariable("id") int id) {
//        dataRowsSecuritiesPagesRepository.deleteById(id);
//    }
//
//    @RequestMapping(value = {"/putSecuritiesPaper"}, method = RequestMethod.PUT)
//    @ResponseStatus(HttpStatus.OK)
//    public void putDataRowsSecuritiesPages(@RequestBody DataRowsSecuritiesPages d) {
//        dataRowsSecuritiesPagesRepository.save(d);
//    }

}
