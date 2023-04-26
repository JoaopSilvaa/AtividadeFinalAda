package br.ada.schedule.user;

import br.ada.schedule.common.I18nUtils;
import br.ada.schedule.user.exception.UserException;
import jakarta.validation.Valid;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

@Controller
@RequestMapping("/app/users")
public class UserController {

    private UserService service;
    private MessageSource messages;

    public UserController(UserService service, MessageSource messages) {
        this.service = service;
        this.messages = messages;
    }

    @GetMapping
    public String get(Model model) {
        model.addAttribute("users", service.list());
        return "user/list";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("user", new User());
        return "user/form";
    }

    @GetMapping("/edit/{id}")
    public String edit(
            @PathVariable("id") Long id,
            Model model
    ) {
        User user = service.findById(id).get();
        model.addAttribute("user", user);
        return "user/form";
    }

    @PostMapping
    public String save(
            @Valid @ModelAttribute User user,
            BindingResult result,
            WebRequest request
    ) {
        if (!result.hasErrors()) {
            try {
                if (user.getId() == null) {
                    service.create(user);
                } else {
                    service.update(user);
                }
            } catch (UserException exception) {
                result.addError(
                        convert(exception, request)
                );
            }
        }
        return result.hasErrors() ? "user/form" : "redirect:/app/users";
    }

    private ObjectError convert(UserException ex, WebRequest request) {
        String key = I18nUtils.recoveryKey(ex.getClass());
        return new FieldError(
                "user",
                ex.getRejectField(),
                ex.getRejectedValue(),
                false,
                null,
                null,
                messages.getMessage(
                        key, null, request.getLocale()
                )
        );
    }

}
