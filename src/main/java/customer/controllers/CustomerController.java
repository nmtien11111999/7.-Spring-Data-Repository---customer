package customer.controllers;

import customer.models.Customer;
import customer.models.Province;
import customer.services.ICustomerService;
import customer.services.IProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping(value = "/customers")
public class CustomerController {
    @Autowired
    private ICustomerService customerService;

    @Autowired
    private IProvinceService provinceService;

    @ModelAttribute("provinces")
    public Iterable<Province> listProvinces() {
        return provinceService.findAll();
    }

    @GetMapping(value = "/list")
    public String list(ModelMap modelmap,@PageableDefault(value = 5) Pageable pageable) {
        modelmap.addAttribute("customers", customerService.findAll(pageable));
        return "customerList";
    }

    @GetMapping("/search")
    public String listCustomersSearch(@RequestParam("search") Optional<String> search, @PageableDefault(value = 5) Pageable pageable , ModelMap modelmap){
        Page<Customer> customers;
        if(search.isPresent()){
            customers = customerService.findAllByNameContaining(pageable, search.get());
        } else {
            customers = customerService.findAll(pageable);
        }
        modelmap.addAttribute("customers", customers);
        return "customerList";
    }

    @GetMapping(value = "/create")
    public String add(ModelMap modelmap) {
        modelmap.addAttribute("customer", new Customer());
        return "customerCreate";
    }

    @PostMapping(value = "/create")
    public String save(@ModelAttribute("customer") Customer customer , RedirectAttributes redirectattributes) {
        customerService.save(customer);
        redirectattributes.addFlashAttribute("message", "Customer saved successfully");
        return "redirect:/customers/list";
    }

    @GetMapping(value = "/edit/{id}")
    public String editForm(@PathVariable Long id , ModelMap modelmap) {
        Optional<Customer> customer = customerService.findById(id);
        if (customer.isPresent()) {
            modelmap.addAttribute("customer", customer.get());
            return "customerEdit";
        }
        return "error_404";
    }

    @PostMapping(value = "/edit/{id}")
    public String edit(@ModelAttribute("customer") Customer customer, RedirectAttributes redirectattributes) {
        customerService.save(customer);
        redirectattributes.addFlashAttribute("message", "Customer updated successfully");
        return "redirect:/customers/list";
    }

    @PostMapping(value = "/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes redirectattributes) {
        customerService.remove(id);
        redirectattributes.addFlashAttribute("message", "Customer deleted successfully");
        return "redirect:/customers/list";
    }
}
