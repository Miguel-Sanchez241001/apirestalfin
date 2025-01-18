package pe.com.alfin.alfinapirest.tranversal.dto.ws;

import lombok.NoArgsConstructor;

@lombok.Data

@NoArgsConstructor
public class WsResponse<T> {

    private T response;
    private String codRpta;
    private String descripRpta;
    public WsResponse(T response, String codRpta, String descripRpta) {
        this.response = response;
        this.codRpta = codRpta;
        this.descripRpta = descripRpta;
    }
}
