package edu.epam.mentoring.task8.controller;

import edu.epam.mentoring.task8.model.Account;
import edu.epam.mentoring.task8.service.AccountService;
import edu.epam.mentoring.task8.validator.AccountValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.springframework.web.bind.annotation.RequestMethod.*;

/**
 * Created by eugen on 17.09.2016.
 */
@Controller
@RequestMapping("/account")
public class AccountController {
    @Autowired
    private AccountValidator accountValidator;

    @Autowired
    private AccountService accountService;

    @RequestMapping(method = POST)
    public String createOrEditAccount(@ModelAttribute("account") Account account,
                                Model model, BindingResult bindingResult) {
        accountValidator.validate(account, bindingResult);

        if (bindingResult.hasErrors()) {
            model.addAttribute("account", account);
            return "account-form";
        }

        Number id;
        if (account.getId() == 0) {
            id = accountService.save(account);
        } else {
            id = account.getId();
            accountService.update(account);
        }

        if (id == null || id.longValue() == 0) {
            return "error";
        }

        return "redirect:/account/" + id;
    }

    @RequestMapping(method = GET)
    public String accountForm(Model model) {
        model.addAttribute("account", new Account());
        return "account-form";
    }

    @RequestMapping(value = "/{id}/remover", method = GET)
    public String deleteAccountConfirm(@PathVariable long id, Model model) {
        model.addAttribute("id", id);
        return "delete-confirmation-form";
    }

    @RequestMapping(value = "/{id}/remover", method = POST)
    public String deleteAccount(@PathVariable long id, Model model) {
        if (!accountService.deleteById(id)) {
            return "error";
        }
        return "redirect:/account/list";
    }

    @RequestMapping(value = "/{id}", method = GET)
    public String getAccount(@PathVariable long id, Model model) {
        model.addAttribute("account", accountService.getById(id));
        return "account";
    }

    @RequestMapping(value = "/{id}/editor", method = GET)
    public String editAccount(@PathVariable long id, Model model) {
        model.addAttribute("account", accountService.getById(id));
        return "account-form";
    }

    @RequestMapping(value = "/list", method = GET)
    public String getAllAccounts(Model model) {
        model.addAttribute("accounts", accountService.getAllAccounts());
        return "accounts";
    }

    @RequestMapping(value = "/{id}/balance", method = GET)
    public String editAccountBalanceForm(@PathVariable long id, Model model) {
        model.addAttribute("account", accountService.getById(id));
        return "account-balance-form";
    }

    @RequestMapping(value = "/{id}/balance", method = POST)
    public String editAccountBalance(@ModelAttribute("account") @Validated Account account,
                                     Model model, BindingResult bindingResult) {
        accountValidator.validate(account, bindingResult);

        if (bindingResult.hasErrors()) {
            model.addAttribute("account", account);
            return "account-balance-form";
        }

        accountService.update(account);

        return "redirect:/account/" + account.getId();
    }
}
