package com.ifstatic.mrbilling.view.transaction_detail;

import static com.ifstatic.mrbilling.utilities.DateFormat.getCurrentDate;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.ifstatic.mrbilling.R;
import com.ifstatic.mrbilling.comman.models.TransactionModel;
import com.ifstatic.mrbilling.databinding.ActivityTransactionDetailBinding;
import com.itextpdf.io.IOException;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;


import java.io.File;
import java.io.FileOutputStream;



 public class TransactionDetailActivity extends AppCompatActivity {

    private ProgressDialog progressDialog;
    private Context context;
    private ActivityTransactionDetailBinding binding;
    private TransactionModel mTransactionModel;
    private String templateContent = "";
    private String type = "PRINT";
    private String mUsername;

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
            mTransactionModel = bundle.getParcelable("transaction_data");
        }
    }

    private void initViews() {

        if (mTransactionModel != null) {

            binding.mrNoTextView.setText("#" + mTransactionModel.getMrNo());
            binding.dateTextView.setText(mTransactionModel.getDate());
            binding.nameTextView.setText(mTransactionModel.getParty());
            binding.addressTextView.setText(mTransactionModel.getAddress());
            binding.amountTextView.setText(mTransactionModel.getAmount());
            binding.paymentModeTextView.setText(mTransactionModel.getPaymentMode());

            if (mTransactionModel.getChequeDetail() != null) {
                binding.datePaymentDetailTextView.setText(mTransactionModel.getChequeDetail().getDate());
                binding.bankNameTextView.setText(mTransactionModel.getChequeDetail().getBankName());
                binding.chequeNoTextView.setText(mTransactionModel.getChequeDetail().getChequeNo());
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

                generatePDFForPrint(mTransactionModel, mUsername);
            }
        });
        binding.shareTransactionTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               generatePDFForShare(mTransactionModel, mUsername);
            }
        });
    }



    public void generatePDFForPrint(TransactionModel transactionModel, String username) {
        PdfWriter writer = null;
        try {
            String downloadsPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath();

            // Create a file with a unique name in the Downloads directory
            String fileName = "bill" + transactionModel.getTransactionId() + ".pdf";
            File file = new File(downloadsPath, fileName);

            // Create a PDF writer instance
            writer = new PdfWriter(new FileOutputStream(file));

            // Create a PDF document
            PdfDocument pdfDocument = new PdfDocument(writer);
            Document document = new Document(pdfDocument, PageSize.A6);

            // Set the font
            PdfFont font = PdfFontFactory.createFont();

            // Add the title paragraph
            Paragraph title = new Paragraph("MRBilling")
                    .setFont(font)
                    .setFontSize(16)
                    .setBold()
                    .setTextAlignment(TextAlignment.CENTER);
            document.add(title);

            // Add the date paragraph
            Paragraph date = new Paragraph("Date: " + getCurrentDate())
                    .setFont(font)
                    .setFontSize(9)
                    .setMarginBottom(10)
                    .setTextAlignment(TextAlignment.RIGHT);
            document.add(date);

            // Add the input values paragraphs
            Paragraph input1Paragraph = new Paragraph("Id: " + transactionModel.getTransactionId())
                    .setFont(font)
                    .setFontSize(9)
                    .setMarginTop(-23);
            Paragraph input3Paragraph = new Paragraph("By :" + username)
                    .setFont(font)
                    .setFontSize(10)
                    .setMarginTop(10);
            Paragraph input2Paragraph = new Paragraph("To : " + transactionModel.getParty())
                    .setFont(font)
                    .setFontSize(10);
            Paragraph input4Paragraph = new Paragraph("Mode :" + transactionModel.getPaymentMode())
                    .setFont(font)
                    .setFontSize(10);
            Paragraph input5Paragraph = new Paragraph("address : " + transactionModel.getAddress())
                    .setFont(font)
                    .setFontSize(10);

            Paragraph paymentDetails = new Paragraph("Payment Details")
                    .setFont(font)
                    .setFontSize(12)
                    .setBold()
                    .setMarginTop(5)
                    .setMarginBottom(5)
                    .setTextAlignment(TextAlignment.CENTER);

            float[] width = {100f, 100f};
            Table table = new Table(width);
            table.setHorizontalAlignment(HorizontalAlignment.CENTER);

            table.addCell(new Cell().add(new Paragraph("Amount")));
            table.addCell(new Cell().add(new Paragraph("₹ " + transactionModel.getAmount())));

            switch (transactionModel.getPaymentMode()) {
                case "Cheque":
                    table.addCell(new Cell().add(new Paragraph("Cheque No.").setFontSize(10)));
                    table.addCell(new Cell().add(new Paragraph(transactionModel.getChequeDetail().getChequeNo()).setFontSize(10)));

                    table.addCell(new Cell().add(new Paragraph("Bank").setFontSize(10)));
                    table.addCell(new Cell().add(new Paragraph(transactionModel.getChequeDetail().getBankName()).setFontSize(10)));

                    table.addCell(new Cell().add(new Paragraph("Cheque Date").setFontSize(10)));
                    table.addCell(new Cell().add(new Paragraph(transactionModel.getChequeDetail().getDate()).setFontSize(10)));
                    break;
                case "UPI":
                    table.addCell(new Cell().add(new Paragraph("Upi Id").setFontSize(10)));
                    table.addCell(new Cell().add(new Paragraph(transactionModel.getUpiDetail().getUpiId()).setFontSize(10)));

                    table.addCell(new Cell().add(new Paragraph("Date").setFontSize(10)));
                    table.addCell(new Cell().add(new Paragraph(transactionModel.getUpiDetail().getDate()).setFontSize(10)));
                case "Online":
                    table.addCell(new Cell().add(new Paragraph("reference Id").setFontSize(10)));
                    table.addCell(new Cell().add(new Paragraph(transactionModel.getOnlineDetail().getReferenceId()).setFontSize(10)));

                    table.addCell(new Cell().add(new Paragraph("Date").setFontSize(10)));
                    table.addCell(new Cell().add(new Paragraph(transactionModel.getOnlineDetail().getDate()).setFontSize(10)));
                    break;
            }
            // Add the input values paragraphs to the document
            document.add(input1Paragraph);
            document.add(input3Paragraph);
            document.add(input2Paragraph);
            document.add(input5Paragraph);
            document.add(input4Paragraph);
            document.add(paymentDetails);
            document.add(table);
            Toast.makeText(this, "Bill created and Saved in Downloads Folder", Toast.LENGTH_SHORT).show();

            // Close the document
            document.close();


        } catch (IOException | java.io.IOException e) {
            e.printStackTrace();
            // Show a toast or perform any other actions to indicate an error occurred during PDF generation
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (java.io.IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void generatePDFForShare(TransactionModel transactionModel, String username) {
        PdfWriter writer = null;
        try {
            String downloadsPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath();

            // Create a file with a unique name in the Downloads directory
            String fileName = "bill" + transactionModel.getTransactionId() + ".pdf";
            File file = new File(downloadsPath, fileName);

            // Create a PDF writer instance
            writer = new PdfWriter(new FileOutputStream(file));

            // Create a PDF document
            PdfDocument pdfDocument = new PdfDocument(writer);
            Document document = new Document(pdfDocument, PageSize.A6);

            // Set the font
            PdfFont font = PdfFontFactory.createFont();

            // Add the title paragraph
            Paragraph title = new Paragraph(getResources().getString(R.string.app_name))
                    .setFont(font)
                    .setFontSize(16)
                    .setBold()
                    .setTextAlignment(TextAlignment.CENTER);
            document.add(title);

            // Add the date paragraph
            Paragraph date = new Paragraph("Date: " + getCurrentDate())
                    .setFont(font)
                    .setFontSize(9)
                    .setMarginBottom(10)
                    .setTextAlignment(TextAlignment.RIGHT);
            document.add(date);

            // Add the input values paragraphs
            Paragraph input1Paragraph = new Paragraph("Id: " + transactionModel.getTransactionId())
                    .setFont(font)
                    .setFontSize(9)
                    .setMarginTop(-23);
            Paragraph input3Paragraph = new Paragraph("By :" + username)
                    .setFont(font)
                    .setFontSize(10)
                    .setMarginTop(10);
            Paragraph input2Paragraph = new Paragraph("To : " + transactionModel.getParty())
                    .setFont(font)
                    .setFontSize(10);
            Paragraph input4Paragraph = new Paragraph("Mode :" + transactionModel.getPaymentMode())
                    .setFont(font)
                    .setFontSize(10);
            Paragraph input5Paragraph = new Paragraph("address : " + transactionModel.getAddress())
                    .setFont(font)
                    .setFontSize(10);

            Paragraph paymentDetails = new Paragraph("Payment Details")
                    .setFont(font)
                    .setFontSize(12)
                    .setBold()
                    .setMarginTop(5)
                    .setMarginBottom(5)
                    .setTextAlignment(TextAlignment.CENTER);

            float[] width = {100f, 100f};
            Table table = new Table(width);
            table.setHorizontalAlignment(HorizontalAlignment.CENTER);

            table.addCell(new Cell().add(new Paragraph("Amount")));
            table.addCell(new Cell().add(new Paragraph("₹ " + transactionModel.getAmount())));

            switch (transactionModel.getPaymentMode()) {
                case "Cheque":
                    table.addCell(new Cell().add(new Paragraph("Cheque No.").setFontSize(10)));
                    table.addCell(new Cell().add(new Paragraph(transactionModel.getChequeDetail().getChequeNo()).setFontSize(10)));

                    table.addCell(new Cell().add(new Paragraph("Bank").setFontSize(10)));
                    table.addCell(new Cell().add(new Paragraph(transactionModel.getChequeDetail().getBankName()).setFontSize(10)));

                    table.addCell(new Cell().add(new Paragraph("Cheque Date").setFontSize(10)));
                    table.addCell(new Cell().add(new Paragraph(transactionModel.getChequeDetail().getDate()).setFontSize(10)));
                    break;
                case "UPI":
                    table.addCell(new Cell().add(new Paragraph("Upi Id").setFontSize(10)));
                    table.addCell(new Cell().add(new Paragraph(transactionModel.getUpiDetail().getUpiId()).setFontSize(10)));

                    table.addCell(new Cell().add(new Paragraph("Date").setFontSize(10)));
                    table.addCell(new Cell().add(new Paragraph(transactionModel.getUpiDetail().getDate()).setFontSize(10)));
                case "Online":
                    table.addCell(new Cell().add(new Paragraph("reference Id").setFontSize(10)));
                    table.addCell(new Cell().add(new Paragraph(transactionModel.getOnlineDetail().getReferenceId()).setFontSize(10)));

                    table.addCell(new Cell().add(new Paragraph("Date").setFontSize(10)));
                    table.addCell(new Cell().add(new Paragraph(transactionModel.getOnlineDetail().getDate()).setFontSize(10)));
                    break;
            }
            // Add the input values paragraphs to the document
            document.add(input1Paragraph);
            document.add(input3Paragraph);
            document.add(input2Paragraph);
            document.add(input5Paragraph);
            document.add(input4Paragraph);
            document.add(paymentDetails);
            document.add(table);
            Toast.makeText(this, "Bill created and Saved in Downloads Folder", Toast.LENGTH_SHORT).show();

            // Close the document
            document.close();

            sharePDF(file,TransactionDetailActivity.this);

        } catch (IOException | java.io.IOException e) {
            e.printStackTrace();
            // Show a toast or perform any other actions to indicate an error occurred during PDF generation
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (java.io.IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
     public void sharePDF(File file, Activity activity) {
         if (file.exists()) {
             // Get the FileProvider authority
             String authority = activity.getPackageName() + ".fileprovider";

             // Generate the content URI for the file
             Uri uri = FileProvider.getUriForFile(activity, authority, file);

             // Create a sharing intent
             Intent intent = new Intent(Intent.ACTION_SEND);
             intent.setType("application/pdf");
             intent.putExtra(Intent.EXTRA_STREAM, uri);
             intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

             // Start the sharing activity
             activity.startActivity(Intent.createChooser(intent, "Share PDF"));
         } else {
             System.out.println("PDF file does not exist.");
         }
     }
}