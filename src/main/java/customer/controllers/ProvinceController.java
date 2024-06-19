package customer.controllers;

import customer.models.Customer;
import customer.models.Province;
import customer.services.ICustomerService;
import customer.services.IProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/provinces")
public class ProvinceController {
    @Autowired
    private IProvinceService provinceService;

    @Autowired
    private ICustomerService customerService;

    @GetMapping(value ="/list")
    public String listProvince(ModelMap modelMap) {
        Iterable<Province> provinces = provinceService.findAll();
        modelMap.addAttribute("provinces", provinces);
        return "provinceList";
    }

    @GetMapping("/create")
    public String createForm(ModelMap modelMap) {
        modelMap.addAttribute("province",new Province());
        return "provinceCreate";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute("province") Province province,
                         RedirectAttributes redirectAttributes) {
        provinceService.save(province);
        redirectAttributes.addFlashAttribute("message", "Create new province successfully");
        return "redirect:/provinces/list";
    }

    @GetMapping("/edit/{id}")
    public String updateForm(@RequestParam Long id , ModelMap modelmap) {
        Optional<Province> province = provinceService.findById(id);
        if (province.isPresent()) {
            modelmap.addAttribute("province", province.get());
            return "provinceUpdate";
        } else {
            return "/error_404";
        }
    }

    @PostMapping("/edit/{id}")
    public String update(@ModelAttribute("province") Province province,
                         RedirectAttributes redirect) {
        provinceService.save(province);
        redirect.addFlashAttribute("message", "Update province successfully");
        return "redirect:/provinces/list";
    }

    @GetMapping("/view-province/{id}")
    public String viewProvince(@RequestParam("id") Long id , ModelMap modelMap){
        Optional<Province> provinceOptional = provinceService.findById(id);
        if(!provinceOptional.isPresent()){
            return "error_404";
        }

        Iterable<Customer> customers = customerService.findAllByProvince(provinceOptional.get());
        modelMap.addAttribute("customers", customers);
        return "customerList";
    }
}
