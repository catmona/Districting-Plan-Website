package com.mavericks.server;

import com.mavericks.server.entity.CensusBlock;
import com.mavericks.server.repository.CensusBlockRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class PostProcessing {

    @Autowired
    public static CensusBlockRepository repo;

    public static void main(String[]args){
        List<CensusBlock> blocks = repo.findAll();
        for(CensusBlock cb:blocks){
            boolean isBorderBlock=false;
            for(CensusBlock neighbor : cb.getNeighbors()){
                if(cb.getDistrictId()!=neighbor.getDistrictId()){
                    isBorderBlock=true;
                }
            }
            cb.setBorderBlock(isBorderBlock);
        }


    }
}
