package com.example.blooddonation.service;

import com.example.blooddonation.entity.Requests;
import com.example.blooddonation.repository.IRequestsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RequestsService {

    @Autowired
    IRequestsRepository iRequestsRepository;

    /**
     *
     * @return A list with all requests from the db.
     */
    public List<Requests> getAll() {
        return (List<Requests>) iRequestsRepository.findAll();
    }

    /**
     *
     * @param id of the request we want to find
     * @return The request with the specified id if it exists or else null.
     */
    public Requests getById(Integer id) {
        Optional<Requests> requests = iRequestsRepository.findById(id);
        return requests.orElse(null);
    }

    /**
     *
     * @param requests this is the requests object with all its fields except id
     * @return Returns the saved request if it was possible to save it, else returns null.
     */
    public Requests saveRequest(Requests requests){
        requests.setRequestDate(new Date());
        return iRequestsRepository.save(requests);
    }

    /**
     *
     * @param requests this is the requests object with all its fields with id
     * @ Returns the edited request if it was possible to save it, else returns null.
     */
    public Requests editRequest(Requests requests){

        return iRequestsRepository.save(requests);
    }

    /**
     *
     * @param id - the id of the request we want to delete
     *  This method deleted the request with the specified id and sends an e-mail with the details
     *   deleted appointment.
     */
    public void deleteRequest(Integer id){
        iRequestsRepository.deleteById(id);
    }

    /**
     *
     * @param id - the id of the request we want to change status
     */
    public void changeConfirmed(Integer id){
        Requests request= this.getById(id);

        request.setConfirmed(1);

        iRequestsRepository.save(request);
    }

    /**
     *
     * @return  Returns a list with all unconfirmed requests.
     */
    public List<Requests> getAllUnconfirmed() {

        return (List<Requests>) iRequestsRepository.findAll().stream().filter(o -> o.getConfirmed() == 0).collect(Collectors.toList());
    }

    /**
     *
     * @return Returns a list with all confirmed requests.
     */
    public List<Requests> getAllConfirmed() {

        return (List<Requests>) iRequestsRepository.findAll().stream().filter(o -> o.getConfirmed() == 1).collect(Collectors.toList());
    }

}
