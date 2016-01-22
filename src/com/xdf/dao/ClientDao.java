package com.xdf.dao;

import com.xdf.dto.Client;

public interface ClientDao {
	/**
	 * insert a new client into database
	 * @param client
	 * @return insert result
	 */
	public Integer insertClient(Client client);
}
