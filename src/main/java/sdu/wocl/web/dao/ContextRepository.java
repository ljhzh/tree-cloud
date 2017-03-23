package sdu.wocl.web.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import sdu.wocl.web.bean.Context;

@Repository
public interface ContextRepository extends JpaRepository<Context, Long>{

    Context findByTitle(String title);

    @Query("from Context c where c.contextid=:id")
    Context findByContextId(@Param("id")int id);
    
    @Query("from Context c where c.context like %:context%")
    Context findByContext(@Param("context")String context);

    @Query("update Context c set c.iscollected=:status where c.contextid=:id")
    @Modifying
    @Transactional
    void updateStatus(@Param("id")int id,@Param("status")int status); 
    
    Page<Context> findAll(Pageable pageRequest);
    
    @Query("from Context c where c.iscollected!=1")
    Page<Context> findAllUnRecord(Pageable pageRequest);
    
    @Query("from Context c where c.iscollected=2")
    Page<Context> findAllRead(Pageable pageRequest);
    
    
    @Query("select c.type from Context c group by c.type order by count(c.type) desc")
    List<String> findTypes();
   
}
