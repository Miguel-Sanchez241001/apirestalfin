package pe.com.alfin.alfinapirest.tranversal.dto.ws;

import lombok.NoArgsConstructor;

@lombok.Data
@NoArgsConstructor
public class WsRequest <T>{

    private T request;

    public WsRequest(T request) {
        super();
        this.request = request;


    }
}
