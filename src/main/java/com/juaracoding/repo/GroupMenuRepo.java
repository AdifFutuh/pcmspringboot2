package com.juaracoding.repo;

import com.juaracoding.model.GroupMenu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupMenuRepo extends JpaRepository<GroupMenu, Long> {

    /** Select * From MstGroupMenu WHERE toLower(Email) Like toLower('%chihuy%') */
    public Page<GroupMenu> findByNamaContainsIgnoreCase(String nama, Pageable pageable);
//    /** Select * From MstGroupMenu WHERE toLower(Email) Like toLower('%chihuy%') AND CreatedDate Between */
//    public Page<GroupMenu> findByNamaContainsIgnoreCaseAndCreatedDateBetween(String nama, Pageable pageable,String from, String to);
    public Page<GroupMenu> findByDeskripsiContainsIgnoreCase(String nama, Pageable pageable);
}
