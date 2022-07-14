package com.example.blooddonation.service;


import com.example.blooddonation.entity.Stock;
import com.example.blooddonation.repository.IStockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StockService {

    @Autowired
    IStockRepository iStockRepository;

    /**
     *
     * @return A list with all stocks from the db.
     */
    public List<Stock> getAll() {
        return (List<Stock>) iStockRepository.findAll();
    }

    /**
     *
     * @param id- id of the stock we want to find
     * @return The stock with the specified id if it exists or else null.
     */
    public Stock getById(Integer id) {
        Optional<Stock> stock = iStockRepository.findById(id);
        return stock.orElse(null);
    }

    /**
     *
     * @param stock - this is the stock object with all its fields except id
     * @return Returns the saved stock if it was possible to save it, else returns error.
     */
    public Stock saveStock(Stock stock){
        return iStockRepository.save(stock);
    }

    /**
     *
     * @param stock- this is the stock object with all its fields with id
     * @return Returns the edited stock if it was possible to save it, else returns error.
     */
    public Stock editStock(Stock stock){

        return iStockRepository.save(stock);
    }

}
