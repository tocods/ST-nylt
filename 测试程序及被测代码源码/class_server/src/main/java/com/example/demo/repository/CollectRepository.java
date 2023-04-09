package com.example.demo.repository;

import com.example.demo.entity.Collect;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface CollectRepository extends JpaRepository<Collect, Integer> {
    @Query(value = "from Collect where userid = :userid")
    List<Collect> getCollectByUserId(@Param("userid") int userId);


    @Transactional
    @Modifying
    @Query(value = "delete from Collect where id = :id ",nativeQuery = true)
    void deleteByCollectId(@Param("id") int collectId);

    @Query(value = "from Collect where classid = :cid and userid = :uid")
    Collect getByLikedUserIdAndLikedPostId(@Param("cid") int likedClassId, @Param("uid") int likedPostId);
}