package br.com.gustavo.msavaliadorcredito.application;

import br.com.gustavo.msavaliadorcredito.application.ex.DadosClienteNotFoundException;
import br.com.gustavo.msavaliadorcredito.application.ex.ErroComunicacaoMicroservicesException;
import br.com.gustavo.msavaliadorcredito.domain.model.CartaoCliente;
import br.com.gustavo.msavaliadorcredito.domain.model.DadosCliente;
import br.com.gustavo.msavaliadorcredito.domain.model.SituacaoCliente;
import br.com.gustavo.msavaliadorcredito.infra.clients.CartoesResourceClient;
import br.com.gustavo.msavaliadorcredito.infra.clients.ClienteResourceClient;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AvaliadorCreditoService {

    private final ClienteResourceClient clientesClient;

    private final CartoesResourceClient cartoesClient;

    public SituacaoCliente obterSituacaoCliente(String cpf) throws DadosClienteNotFoundException, ErroComunicacaoMicroservicesException {
        try {
            ResponseEntity<DadosCliente> dadosClienteResponse = clientesClient.dadosCliente(cpf); // obterDadosCliente - msclientes
            ResponseEntity<List<CartaoCliente>> cartoesResponse = cartoesClient.getCartoesByCliente(cpf); // obter cartoes do cliente - mscartoes
            return SituacaoCliente
                    .builder()
                    .cliente(dadosClienteResponse.getBody())
                    .cartoes(cartoesResponse.getBody())
                    .build();
        } catch (FeignException.FeignClientException e) {
            int status = e.status();
            if (HttpStatus.NOT_FOUND.value() == status) {
                throw new DadosClienteNotFoundException();
            }
            throw new ErroComunicacaoMicroservicesException(e.getMessage(), status);
        }
    }
}