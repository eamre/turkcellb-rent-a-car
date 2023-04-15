package kodlama.io.rentacar.business.concretes;

import kodlama.io.rentacar.business.abstracts.InvoiceService;
import kodlama.io.rentacar.business.dto.requests.create.CreateInvoiceRequest;
import kodlama.io.rentacar.business.dto.requests.update.UpdateInvoiceRequest;
import kodlama.io.rentacar.business.dto.responses.create.CreateInvoiceResponse;
import kodlama.io.rentacar.business.dto.responses.get.GetAllInvoicesResponse;
import kodlama.io.rentacar.business.dto.responses.get.GetInvoiceResponse;
import kodlama.io.rentacar.business.dto.responses.update.UpdateInvoiceResponse;
import kodlama.io.rentacar.entities.concretes.Invoice;
import kodlama.io.rentacar.repository.abstracts.InvoiceRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
@AllArgsConstructor
@Service
public class InvoiceManager implements InvoiceService {
    private InvoiceRepository repository;
    private ModelMapper mapper;
    @Override
    public List<GetAllInvoicesResponse> getAll() {
        List<Invoice> invoices = repository.findAll();
        List<GetAllInvoicesResponse> responses = invoices
                .stream()
                .map(invoice -> mapper.map(invoice, GetAllInvoicesResponse.class)).toList();

        return responses;
    }

    @Override
    public GetInvoiceResponse getById(int id) {
        checkIfInvoiceExists(id);
        Invoice invoice = repository.findById(id).orElseThrow();
        GetInvoiceResponse response = mapper.map(invoice,GetInvoiceResponse.class);

        return response;
    }

    @Override
    public CreateInvoiceResponse add(CreateInvoiceRequest request) {
        Invoice invoice = mapper.map(request, Invoice.class);
        invoice.setId(0);
        invoice.setTotalPrice(getTotalPrice(invoice));
        repository.save(invoice);

        CreateInvoiceResponse response = mapper.map(invoice,CreateInvoiceResponse.class);
        return response;
    }

    @Override
    public UpdateInvoiceResponse update(int id, UpdateInvoiceRequest request) {
        checkIfInvoiceExists(id);
        Invoice invoice = mapper.map(request,Invoice.class);
        invoice.setId(id);
        invoice.setTotalPrice(getTotalPrice(invoice));

        repository.save(invoice);
        UpdateInvoiceResponse response = mapper.map(invoice,UpdateInvoiceResponse.class);
        return response;
    }

    @Override
    public void delete(int id) {
        checkIfInvoiceExists(id);
        repository.deleteById(id);
    }

    private void checkIfInvoiceExists(int id){
        if(!repository.existsById(id)){
            throw new RuntimeException("Böyle bir fatura bulunamadı!");
        }
    }

    private double getTotalPrice(Invoice invoice) {
        return invoice.getDailyPrice() * invoice.getRentedForDays();
    }

}
