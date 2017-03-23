package sdu.wocl.web.dao;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sdu.wocl.web.bean.Style;

@Repository
public interface StyleRepository extends JpaRepository<Style, Long> {
    public Page<Style> findAll(Pageable pageRequest);
}
