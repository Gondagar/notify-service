package club.renthash.notify.service;


import club.renthash.notify.model.Block;
import club.renthash.notify.repository.BlockReposotory;
import club.renthash.notify.util.NotifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlockService {



    @Autowired
    BlockReposotory blockReposotory;

    public void save(Block block) { }

    public Block getOne(Long id) { return blockReposotory.findById(id).orElseThrow(() -> new IllegalArgumentException("Entity not found")); }

    public List<Block> getAll() { return blockReposotory.findAll(); }

    public void delete(Block block) { blockReposotory.delete(block); }


}
