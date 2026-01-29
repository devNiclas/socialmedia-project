package se.jensen.niclas.springbootrestapi.dto;

/**
 * A record that represents a request for login, when the user is going to log in
 *
 * @param username the users username
 * @param password the users password
 */

public record LoginRequestDTO(String username, String password) {
}
