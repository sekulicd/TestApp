package com.sekulicd.repo;

import java.util.List;
import com.couchbase.client.protocol.views.Query;
import org.springframework.data.repository.CrudRepository;
import com.sekulicd.domain.User;

public interface UserRepository extends CrudRepository<User, String> {

	 List<User> all(Query query);

}
