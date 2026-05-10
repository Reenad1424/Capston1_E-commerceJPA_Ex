package org.example.ecommercejpa_ex.Repository;

import org.example.ecommercejpa_ex.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<User,Integer> {


}
