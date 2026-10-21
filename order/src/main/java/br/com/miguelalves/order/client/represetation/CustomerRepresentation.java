package br.com.miguelalves.order.client.represetation;

public record CustomerRepresentation(
        Long id,
        String name,
        String cpf,
        String street,
        String number,
        String neighborhood,
        String email,
        String phone) {
}
