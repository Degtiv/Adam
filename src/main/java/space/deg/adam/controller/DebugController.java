package space.deg.adam.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import space.deg.adam.domain.Category;
import space.deg.adam.domain.Transaction;
import space.deg.adam.domain.User;
import space.deg.adam.repository.CategoryRepository;
import space.deg.adam.repository.TransactionRepository;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Controller
public class DebugController {
    @Value("${upload.path}")
    private String uploadPath;

    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping("/debug")
    public String debug (Model model) {
        Iterable<Transaction> transactions = transactionRepository.findAll();
        model.addAttribute("transactions", transactions);

        return "debug";
    }

    //TODO: rewrite milestone logic, rewrite category logic
    @PostMapping("/debug")
    public String add (
            @AuthenticationPrincipal User user,
            @RequestParam String name,
            @RequestParam String dateText,
            @RequestParam BigDecimal value,
            @RequestParam String description,
            @RequestParam Integer status,
            @RequestParam String categoryName,
            @RequestParam("file") MultipartFile file,
            Model model) throws IOException {

        Category category = categoryRepository.findByName(categoryName);
        SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd");


        Date date = null;
        try {
            date = ft.parse(dateText);
        }catch (ParseException e) {
            System.out.println("Ошибка при получении даты");
        }

        Transaction transaction = new Transaction(name, date, value, description, status, null, category, user);

        if (file != null && !file.getOriginalFilename().isEmpty()) {
            File uploadDir = new File (uploadPath);

            if (!uploadDir.exists())
                uploadDir.mkdir();

            String uuidFile = UUID.randomUUID().toString();
            String resultFilename = uuidFile + "." + file.getOriginalFilename();

            file.transferTo(new File(uploadPath + "/" + resultFilename));
            transaction.setImage(resultFilename);
        }


        transactionRepository.save(transaction);
        Iterable<Transaction> transactions = transactionRepository.findAll();
        model.addAttribute("transactions", transactions);

        return "debug";
    }
}
