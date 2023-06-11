package com.ifstatic.mrbilling.view.transaction_detail;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.print.PdfManager;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintManager;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebView;

import androidx.appcompat.app.AppCompatActivity;

import com.ifstatic.mrbilling.comman.models.TransactionModel;
import com.ifstatic.mrbilling.databinding.ActivityTransactionDetailBinding;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;


public class TransactionDetailActivity extends AppCompatActivity {

    private ProgressDialog progressDialog;
    private ActivityTransactionDetailBinding binding;
    private TransactionModel transactionModel;

    WebView webViewTemp;
    private String templateContent = "";
    private String type="PRINT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTransactionDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.webViewTemp.getSettings().setAllowFileAccess(true);

        initListeners();
        getBundles();
        initViews();
    }

    private void getBundles() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            transactionModel = bundle.getParcelable("transaction_data");
        }
    }

    private void initViews() {

        if (transactionModel != null) {

            binding.mrNoTextView.setText("#" + transactionModel.getMrNo());
            binding.dateTextView.setText(transactionModel.getDate());
            binding.nameTextView.setText(transactionModel.getParty());
            binding.addressTextView.setText(transactionModel.getAddress());
            binding.amountTextView.setText(transactionModel.getAmount());
            binding.paymentModeTextView.setText(transactionModel.getPaymentMode());

            if (transactionModel.getChequeDetail() != null) {
                binding.datePaymentDetailTextView.setText(transactionModel.getChequeDetail().getDate());
                binding.bankNameTextView.setText(transactionModel.getChequeDetail().getBankName());
                binding.chequeNoTextView.setText(transactionModel.getChequeDetail().getChequeNo());
            } else {
                binding.paymentDetailsLayout.setVisibility(View.GONE);
                binding.paymentDetailsTextView.setVisibility(View.GONE);
            }

        }
    }

    private void initListeners() {

        binding.header.titleTextView.setText("Transaction Details");

        binding.header.backImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        binding.printTransactionTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                type = "PRINT";
               checkWebview();
                printPdf();
            }
        });
    }
    private void checkWebview(){

        webViewTemp.loadDataWithBaseURL(null, generateHtml(templateContent), "text/html", "utf-8", null);

    }
    private String generateHtml(String text) {
        String[] parts = text.toString().split("\n");
        StringBuilder html;

        if (TextUtils.equals(type,"PREVIEW")){
            html= new StringBuilder("<div>");
        }else {
            html= new StringBuilder("<div style=\"font-size:36px;\">");
        }

        for (String part : parts) {
            if (TextUtils.isEmpty(part)) {
                html.append("<br>");
            } else {
                html.append(replaceTags(part));
            }
        }

        html.append("</div>");

        return html.toString();
    }

    private void printPdf() {

        File filePath=new File(getCacheDir(),"pdf");

        if (!filePath.exists()){
            filePath.mkdir();
        }

        String filename= transactionModel.getMrNo() + "-" + transactionModel.getParty()+"-"+transactionModel.getDate() + ".pdf";

        File f = new File(filePath, filename);

        PdfManager.generatePDFFromWebView(f, webViewTemp, PrintAttributes.MediaSize.NA_LETTER, new PdfManager.OnPDFPrintListener() {
            @Override
            public void onSuccess(File file) {
                PrintManager printManager = (PrintManager)getSystemService(Context.PRINT_SERVICE);
                PrintDocumentAdapter printAdapter;
                printAdapter = webViewTemp.createPrintDocumentAdapter(filename);
                PrintAttributes.Builder builder = new PrintAttributes.Builder();
                builder.setMinMargins(PrintAttributes.Margins.NO_MARGINS);
                builder.setMediaSize(PrintAttributes.MediaSize.ISO_A4);
                printManager.print(file.getName(), printAdapter, builder.build());

            }

            @Override
            public void onError(Exception exception) {
                progressDialog.dismiss();
            }
        });
    }

    private String replaceTags(String content){
        if (content.contains("#CENTER#")){
            content="<div style=\"text-align:center;\">"+content.replace("#CENTER#","").replace("#LEFT#","").replace("#RIGHT#","");
        }else if (content.contains("#RIGHT#")){
            content="<div style=\"text-align:right;\">"+content.replace("#RIGHT#","").replace("#LEFT#","");
        }else if (content.contains("#LEFT#")){
            content="<div style=\"text-align:left;\">"+content.replace("#LEFT#","");
        }else{
            content="<div style=\"text-align:left;\">"+content;
        }

        content=content.replace("#MR-NO#", transactionModel.getMrNo());
        content=content.replace("#NAME#", transactionModel.getParty());

        content=content.replace("#ADDRESS", transactionModel.getAddress());
        content=content.replace("#AMOUNT#", transactionModel.getAmount());
        content=content.replace("#PAYMENT-MODE#", transactionModel.getPaymentMode());

        SimpleDateFormat sdf=new SimpleDateFormat("MMM dd, yyyy");
        SimpleDateFormat sdf1=new SimpleDateFormat("MMM dd, yyyy HH:mm:ss");
        sdf1.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date date=new Date();
        content=content.replace("#TODAY#",sdf.format(date));
        content=content.replace("#TODAY-TIME#",sdf1.format(date));

        content=content+"</dvi>";

        return content;
    }



}