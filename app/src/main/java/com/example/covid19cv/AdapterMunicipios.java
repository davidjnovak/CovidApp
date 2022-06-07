package com.example.covid19cv;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.ArrayList;

public class AdapterMunicipios extends RecyclerView.Adapter<AdapterMunicipios.ViewHolder> {

    private ItemClickListener mClickListener;

    public Municipio getItem(int position) {
        return municipios.get(position);
    }

    interface ItemClickListener{
        void onRVItemClick(View view, int position);
    }
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    private ArrayList<Municipio> municipios;
    Context context;
    public AdapterMunicipios(ArrayList<Municipio> parMunicipality) throws JSONException{
        this.municipios = parMunicipality;
    }

    public void Init() {
        // We read the JSON file and fill the “municipios” ArrayList
        municipios=new ArrayList<Municipio>();
        InputStream is = context.getResources().openRawResource(R.raw.data);
        Writer writer = new StringWriter();
        char[] buffer = new char[1024];
        try {
            Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            int n;
            while ((n = reader.read(buffer)) != -1) {
                writer.write(buffer, 0, n);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //The String writer.toString() must be parsed in the municipalities ArrayList using JSONArray and JSONObject
    }

    @Override
    public int getItemCount() {
        return municipios.size();
    }
    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView textView;
        public ViewHolder(View view) {
            super(view);
            textView = (TextView) view.findViewById(R.id.code);
            view.setOnClickListener(this);

        }
        public TextView getTextView() {
            return textView;
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null)
                mClickListener.onRVItemClick(view, getAdapterPosition());
        }
    }
    // Create new views (invoked by the layout manager)

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.header, viewGroup, false);
        return new ViewHolder(view);
    }
    private View.OnClickListener onItemClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) view.getTag();
            System.out.println("clicked"+ view);

        }

    };



    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        holder.getTextView().setText(String.valueOf(municipios.get(position).getMunicipi()));
    }
}