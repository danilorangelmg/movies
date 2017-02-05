package com.br.movies.db;

/**
 * Created by danilo on 12/03/16.
 */
public class Query {

    public static final String QUERY_FIND_MOVIE = "select * from tb_movies";
    public static final String QUERY_AUTO_INCREMENT = "select max(id)+1 from "; //concat table name
}
