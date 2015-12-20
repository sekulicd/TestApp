package com.sekulicd.repo;

import java.util.List;

import com.couchbase.client.protocol.views.Query;
import org.springframework.data.repository.CrudRepository;
import com.sekulicd.domain.UserT;

public interface UserTRepository extends CrudRepository<UserT, String> {

	 List<UserT> all(Query query);

}
