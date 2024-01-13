package com.example.rentallmotorbike.vistas;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.rentallmotorbike.R;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;

import java.io.File;
import java.io.FileOutputStream;

public class FaturaActivity extends AppCompatActivity {

    private String marca, modelo, seguro, localLevantamento, dataLevantamento, localDevolucao, dataDevolucao, matricula;
    private double preco;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fatura);

        marca = getIntent().getStringExtra("marca");
        modelo = getIntent().getStringExtra("modelo");
        seguro = getIntent().getStringExtra("seguro");
        localLevantamento = getIntent().getStringExtra("localLevantamento");
        dataLevantamento = getIntent().getStringExtra("dataLevantamento");
        localDevolucao = getIntent().getStringExtra("localDevolucao");
        dataDevolucao = getIntent().getStringExtra("dataDevolucao");
        preco = getIntent().getDoubleExtra("preco", 0.0);
        matricula = getIntent().getStringExtra("matricula");

        criarFatura();

        exibirFatura();

    }

    private void criarFatura() {
        try {
            File pdfFile = new File(getExternalFilesDir(null), "Fatura.pdf");
            FileOutputStream outputStream = new FileOutputStream(pdfFile);

            PdfWriter writer = new PdfWriter(outputStream);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            document.add(new Paragraph("Fatura"));
            document.add(new Paragraph("Marca: " + marca));
            document.add(new Paragraph("Modelo: " + modelo));
            document.add(new Paragraph("Seguro: " + seguro));
            document.add(new Paragraph("Local de Levantamento: " + localLevantamento));
            document.add(new Paragraph("Data de Levantamento: " + dataLevantamento));
            document.add(new Paragraph("Local de Devolução: " + localDevolucao));
            document.add(new Paragraph("Data de Devolução: " + dataDevolucao));
            document.add(new Paragraph("Preço: " + preco + "€"));
            document.add(new Paragraph("Matricula: " + matricula));

            document.close();

            Toast.makeText(this, "Fatura criada com sucesso", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Erro ao criar fatura", Toast.LENGTH_SHORT).show();
        }
    }

    private void exibirFatura() {

        View faturadetalhes = LayoutInflater.from(this).inflate(R.layout.activity_fatura, null);
        setContentView(faturadetalhes);

        TextView tvMarcaModelo = faturadetalhes.findViewById(R.id.tvMarcaModelo);
        TextView tvSeguro = faturadetalhes.findViewById(R.id.tvSeguro);
        TextView tvLocalLevantamento = faturadetalhes.findViewById(R.id.tvLocalLevantamento);
        TextView tvDataLevantamento = faturadetalhes.findViewById(R.id.tvDataLevantamento);
        TextView tvLocalDevolucao = faturadetalhes.findViewById(R.id.tvLocalDevolucao);
        TextView tvDataDevolucao = faturadetalhes.findViewById(R.id.tvDataDevolucao);
        TextView tvPreco = faturadetalhes.findViewById(R.id.tvPreco);
        TextView tvMatricula = faturadetalhes.findViewById(R.id.tvMatricula);


        tvMarcaModelo.setText(marca + " " + modelo);
        tvSeguro.setText(seguro);
        tvLocalLevantamento.setText(localLevantamento);
        tvDataLevantamento.setText(dataLevantamento);
        tvLocalDevolucao.setText(localDevolucao);
        tvDataDevolucao.setText(dataDevolucao);
        tvPreco.setText(preco + "€");
        tvMatricula.setText(matricula);

    }
}