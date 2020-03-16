/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lab2crud;

/**
 *
 * @author reyno
 */
public class Myinvoice {
    private String InvoiceID;
    private String Description;
    private String UserId;
    private String Amount;
    private String Tax;

    public Myinvoice(String InvoiceID, String Description, String UserId, String Amount, String Tax) {
        this.InvoiceID = InvoiceID;
        this.Description = Description;
        this.UserId = UserId;
        this.Amount = Amount;
        this.Tax = Tax;
    }
    
    public String getInvoiceID() {
        return InvoiceID;
    }

    public void setInvoiceID(String InvoiceID) {
        this.InvoiceID = InvoiceID;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String UserId) {
        this.UserId = UserId;
    }

    public String getAmount() {
        return Amount;
    }

    public void setAmount(String Amount) {
        this.Amount = Amount;
    }

    public String getTax() {
        return Tax;
    }

    public void setTax(String Tax) {
        this.Tax = Tax;
    }
    
}
