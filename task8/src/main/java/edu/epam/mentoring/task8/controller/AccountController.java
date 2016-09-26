package edu.epam.mentoring.task8.controller;

import edu.epam.mentoring.task8.model.Account;
import edu.epam.mentoring.task8.service.AccountService;
import edu.epam.mentoring.task8.util.ModelAndViewUtil;
import edu.epam.mentoring.task8.validator.AccountValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import static edu.epam.mentoring.task8.util.ModelAndViewUtil.*;
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
    public ModelAndView createOrEditAccount(@ModelAttribute("account") Account account, BindingResult bindingResult) {
        accountValidator.validate(account, bindingResult);

        if (bindingResult.hasErrors()) {
            return view("account-form", "account", account);
        }

        Number id;
        if (account.getId() == 0) {
            id = accountService.save(account);
        } else {
            id = account.getId();
            accountService.update(account);
        }

        if (id == null || id.longValue() == 0) {
            return view("error");
        }

        return view("redirect:/account/" + id);
    }

    @RequestMapping(method = GET)
    public ModelAndView accountForm() {
        return view("account-form", "account", new Account());
    }

    @RequestMapping(value = "/{id}/remover", method = GET)
    public ModelAndView deleteAccountConfirm(@PathVariable long id) {
        return view("delete-confirmation-form", "id", id);
    }

    @RequestMapping(value = "/{id}/remover", method = POST)
    public ModelAndView deleteAccount(@PathVariable long id) {
        if (!accountService.deleteById(id)) {
            return view("error");
        }
        return view("redirect:/account/list");
    }

    @RequestMapping(value = "/{id}", method = GET)
    public ModelAndView getAccount(@PathVariable long id) {
        return view("account", "account", accountService.getById(id));
    }

    @RequestMapping(value = "/{id}/editor", method = GET)
    public ModelAndView editAccount(@PathVariable long id) {
        return view("account-form", "account", accountService.getById(id));
    }

    @RequestMapping(value = "/list", method = GET)
    public ModelAndView getAllAccounts() {
        return view("accounts", "accounts", accountService.getAllAccounts());
    }

    @RequestMapping(value = "/{id}/balance", method = GET)
    public ModelAndView editAccountBalanceForm(@PathVariable long id) {
        return view("account-balance-form", "account", accountService.getById(id));
    }

    @RequestMapping(value = "/{id}/balance", method = POST)
    public ModelAndView editAccountBalance(@ModelAttribute("account") Account account, BindingResult bindingResult) {
        accountValidator.validate(account, bindingResult);

        if (bindingResult.hasErrors()) {
            return view("account-balance-form", "account", account);
        }

        accountService.update(account);

        return view("redirect:/account/" + account.getId());
    }
}
