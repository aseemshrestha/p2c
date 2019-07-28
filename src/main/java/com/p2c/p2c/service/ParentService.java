package com.p2c.p2c.service;

import com.p2c.p2c.exception.ResourceNotFoundException;
import com.p2c.p2c.model.Child;
import com.p2c.p2c.model.Parent;
import com.p2c.p2c.model.viewmodel.ParentChild;
import com.p2c.p2c.repository.ChildRepository;
import com.p2c.p2c.repository.ParentRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.*;

@Service
public class ParentService
{
    private final ParentRepository parentRepository;
    private final ChildRepository childRepository;

    public ParentService(ParentRepository parentRepository, ChildRepository childRepository)
    {
        this.parentRepository = parentRepository;
        this.childRepository = childRepository;
    }

    public Parent findParentByUsername(String username)
    {
        Optional<Parent> parent = this.parentRepository.findByUsername(username);
        if (!parent.isPresent()) {
            throw new ResourceNotFoundException("Parent with username:" + username + "NOT FOUND");
        }
        return parent.get();
    }

    public ParentChild getParentWithChild(String username) throws Exception
    {
        long start = System.currentTimeMillis();
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        Callable<Optional<Parent>> parent = () -> parentRepository.findByUsername(username);
        Callable<Optional<List<Child>>> child = () -> childRepository.findChildByParentUsername(username);

        Future<Optional<Parent>> parentFuture = executorService.submit(parent);
        Future<Optional<List<Child>>> childFuture = executorService.submit(child);

        ParentChild parentChild = new ParentChild();
        if (!parentFuture.get().isPresent()) {
            throw new ResourceNotFoundException("No user found for Username:" + username);
        } else {
            parentChild.setFirstName(parentFuture.get().get().getFirstName());
            parentChild.setLastName(parentFuture.get().get().getLastName());
            parentChild.setEmail(parentFuture.get().get().getEmail());
            parentChild.setJoinedOn(parentFuture.get().get().getCreated());

            if (childFuture.get().isPresent()) {
                List<Child> children = childFuture.get().get();
                parentChild.setChild(children);
            } else {
                parentChild.setChild(new ArrayList<>());
            }
        }
        System.out.println("time taken:" + (System.currentTimeMillis() - start) + " ms");
        executorService.shutdown();
        executorService.awaitTermination(4, TimeUnit.SECONDS);
        return parentChild;
    }
}
