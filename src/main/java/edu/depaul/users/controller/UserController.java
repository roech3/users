package edu.depaul.users.controller;

import edu.depaul.users.model.User;
import edu.depaul.users.service.UserService;
import edu.depaul.users.util.WebUtils;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(final UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("users", userService.findAll());
        return "user/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("user") final User user) {
        return "user/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("user") @Valid final User user,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (!bindingResult.hasFieldErrors("fullName") && userService.fullNameExists(user.getFullName())) {
            bindingResult.rejectValue("fullName", "Exists.user.fullName");
        }
        if (!bindingResult.hasFieldErrors("userName") && userService.userNameExists(user.getUserName())) {
            bindingResult.rejectValue("userName", "Exists.user.userName");
        }
        if (!bindingResult.hasFieldErrors("passwordHash") && userService.passwordHashExists(user.getPasswordHash())) {
            bindingResult.rejectValue("passwordHash", "Exists.user.passwordHash");
        }
        if (bindingResult.hasErrors()) {
            return "user/add";
        }
        userService.create(user);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("user.create.success"));
        return "redirect:/users";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") final Long id, final Model model) {
        model.addAttribute("user", userService.get(id));
        return "user/edit";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") final Long id,
            @ModelAttribute("user") @Valid final User user, final BindingResult bindingResult,
            final RedirectAttributes redirectAttributes) {
        final User currentUser = userService.get(id);
        if (!bindingResult.hasFieldErrors("fullName") &&
                !user.getFullName().equalsIgnoreCase(currentUser.getFullName()) &&
                userService.fullNameExists(user.getFullName())) {
            bindingResult.rejectValue("fullName", "Exists.user.fullName");
        }
        if (!bindingResult.hasFieldErrors("userName") &&
                !user.getUserName().equalsIgnoreCase(currentUser.getUserName()) &&
                userService.userNameExists(user.getUserName())) {
            bindingResult.rejectValue("userName", "Exists.user.userName");
        }
        if (!bindingResult.hasFieldErrors("passwordHash") &&
                !user.getPasswordHash().equalsIgnoreCase(currentUser.getPasswordHash()) &&
                userService.passwordHashExists(user.getPasswordHash())) {
            bindingResult.rejectValue("passwordHash", "Exists.user.passwordHash");
        }
        if (bindingResult.hasErrors()) {
            return "user/edit";
        }
        userService.update(id, user);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("user.update.success"));
        return "redirect:/users";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable(name = "id") final Long id,
            final RedirectAttributes redirectAttributes) {
        userService.delete(id);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("user.delete.success"));
        return "redirect:/users";
    }

}
