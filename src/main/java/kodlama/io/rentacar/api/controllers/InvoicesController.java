package kodlama.io.rentacar.api.controllers;

import kodlama.io.rentacar.business.abstracts.InvoiceService;
import kodlama.io.rentacar.business.dto.requests.create.CreateInvoiceRequest;
import kodlama.io.rentacar.business.dto.requests.update.UpdateInvoiceRequest;
import kodlama.io.rentacar.business.dto.responses.create.CreateInvoiceResponse;
import kodlama.io.rentacar.business.dto.responses.get.GetAllInvoicesResponse;
import kodlama.io.rentacar.business.dto.responses.get.GetInvoiceResponse;
import kodlama.io.rentacar.business.dto.responses.update.UpdateInvoiceResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/invoices")
@AllArgsConstructor
public class InvoicesController {
    private InvoiceService invoiceService;

    @GetMapping
    public List<GetAllInvoicesResponse> getAll() {
        return invoiceService.getAll();
    }

    @GetMapping("/{id}")
    public GetInvoiceResponse getById(@PathVariable int id) {
        return invoiceService.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CreateInvoiceResponse add(@RequestBody CreateInvoiceRequest request) {
        return invoiceService.add(request);
    }

    @PutMapping("/{id}")
    public UpdateInvoiceResponse update(@PathVariable int id, @RequestBody UpdateInvoiceRequest request) {
        return invoiceService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        invoiceService.delete(id);
    }

}
