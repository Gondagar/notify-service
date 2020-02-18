package club.renthash.notify.controller;


import club.renthash.notify.model.Block;
import club.renthash.notify.repository.BlockReposotory;
import club.renthash.notify.service.BlockService;
import club.renthash.notify.util.NotifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/block")
public class BlockController {



    @Value("${app.stealer.admin.telegram.chat.id:181426062}")
    String adminChatId;

    @Value("${app.debug:false}")
    boolean isDebugMode;

    @Value("${app.comining.api.key:X5coijZLqzA88X93NdoihHZ}")
    String cominingApiKey;

    @Autowired
    BlockReposotory blockReposotory;



    @Autowired
    BlockService blockService;

    @RequestMapping(method = RequestMethod.GET, path = "/")
    ResponseEntity getAll() {


        List<Block> regions = blockService.getAll();

        return new ResponseEntity(regions, HttpStatus.OK);
    }


    @RequestMapping(method = RequestMethod.GET, path = "/{id}")
    ResponseEntity getOne(@PathVariable Long id) {


        Block block = blockService.getOne(id);


        return new ResponseEntity(block, HttpStatus.OK);


    }

    @RequestMapping(method = RequestMethod.POST, path = "/")
    ResponseEntity addBlock(@RequestBody Block block) {
        System.out.println("New block found " + block);
     //   final LocalDateTime now = LocalDateTime.now();
     //   System.out.println(now);
      //  block.setDate(now);
        new Thread(new NotifyService(block,adminChatId,isDebugMode,cominingApiKey, blockReposotory)).start();  // Стартуємо поток який оновлює статуси нових блоків і дохне піля статусів confirm або reject.

        return new ResponseEntity(HttpStatus.OK);


    }

    @DeleteMapping(path = "/{id}")
    ResponseEntity remove(@PathVariable Long id) {

        final Block block = blockService.getOne(id);
        blockService.delete(block);

        return new ResponseEntity(HttpStatus.OK);


    }


}
