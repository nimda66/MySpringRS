package com.hintze.myspringrs.controller;

public class ItemController {



}


//package trialwork.controller;
//
//        import org.springframework.web.bind.annotation.*;
//        import trialwork.model.Statistics;
//        import trialwork.service.TickService;
//        import org.springframework.beans.factory.annotation.Autowired;
//
//@RestController
//@RequestMapping("/api")
//public class TickRestController {
//
//    @Autowired
//    private TickService tickService;
//
//    @PostMapping("/ticks")
//    public void addTick() {
//        tickService.addTick();
//    }
//
//    @GetMapping("/statistics")
//    public Statistics getStatistics() {
//        return tickService.getStatistics();
//    }
//
//    @GetMapping("/statistics/{instrument_identifier}")
//    public Statistics getStatisticsByInstrument(@PathVariable String instrumentIdentifier) {
//        return tickService.getStaticsByInstrument(instrumentIdentifier);
//    }
//}