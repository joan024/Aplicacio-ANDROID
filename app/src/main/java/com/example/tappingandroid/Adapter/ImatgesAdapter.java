package com.example.tappingandroid.Adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tappingandroid.Constants;
import com.example.tappingandroid.R;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;

/* Es defineix l'adaptador ImatgesAdapter per a una llista de String d'imatges,
que s'utilitzarà per a mostrar imatges en un RecyclerView. */
public class ImatgesAdapter extends RecyclerView.Adapter<ImatgesAdapter.ImagenViewHolder> {
    private List<String> imatges;
    private int localid;

    // El constructor de la classe ImatgesAdapter rep la llista d'imatges i l'identificador del local
    public ImatgesAdapter(List<String> imatges, int localid) {
        this.localid = localid;
        this.imatges = imatges;
    }

    @NonNull
    @Override
    // Es crea la vista a partir del fitxer de disseny "imatge_item.xml".
    public ImagenViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.imatge_item, parent, false);
        return new ImagenViewHolder(itemView);
    }

    @Override
    //es carrega la imatge a través de la ruta del fitxer i es comprova si existeix a través del mètode exists() de la classe File. Si la imatge existeix, s'utilitza la llibreria Picasso per a carregar la imatge a l'ImageView.
    public void onBindViewHolder(@NonNull ImagenViewHolder holder, int position) {
        String imagenPath = imatges.get(position);
        File imagenFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Download"+ Constants.rutaArxiusLocal + localid +"/" + imagenPath);
        if(imagenFile.exists()) {
            Log.d("juliaaaaaaaa", "Ruta imatge:"+String.valueOf(imagenFile));
            Picasso.get().load(imagenFile).into(holder.imageView);
        }
    }

    @Override
    public int getItemCount() {
        return imatges.size();
    }

    static class ImagenViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public ImagenViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageview_imagen);
        }
    }
}


