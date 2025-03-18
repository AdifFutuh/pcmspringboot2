package com.juaracoding.repo;

import com.juaracoding.model.GroupMenu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GroupMenuRepo extends JpaRepository<GroupMenu, Long> {

    /** Select * From MstGroupMenu WHERE toLower(Email) Like toLower('%chihuy%') */
    public Page<GroupMenu> findByNamaContainsIgnoreCase(String nama, Pageable pageable);

    /** Ini untuk Report */
    public List<GroupMenu> findByNamaContainsIgnoreCase(String nama);

//    /** Select * From MstGroupMenu WHERE toLower(Email) Like toLower('%chihuy%') AND CreatedDate Between */
//    public Page<GroupMenu> findByNamaContainsIgnoreCaseAndCreatedDateBetween(String nama, Pageable pageable,String from, String to);
    public Page<GroupMenu> findByDeskripsiContainsIgnoreCase(String nama, Pageable pageable);
    public List<GroupMenu> findByDeskripsiContainsIgnoreCase(String nama);

    /** digunakan hanya untuk unit testing */
    public Optional<GroupMenu> findTopByOrderByIdDesc();
}
