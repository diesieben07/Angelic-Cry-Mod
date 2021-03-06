package demonmodders.crymod.common.core;

import java.util.Arrays;
import java.util.logging.Logger;

import net.minecraftforge.common.Configuration;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

import cpw.mods.fml.common.DummyModContainer;
import cpw.mods.fml.common.LoadController;
import cpw.mods.fml.common.ModMetadata;
import cpw.mods.fml.common.event.FMLConstructionEvent;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.network.FMLNetworkHandler;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkModHandler;
import demonmodders.crymod.common.Crymod;

public class CrymodModContainer extends DummyModContainer {
	
	public CrymodModContainer() {
		super(new ModMetadata());
		
		ModMetadata meta = getMetadata();
		meta.autogenerated = false;
		meta.authorList = Arrays.asList("resinresin", "diesieben07");
		meta.name = "Summoningmod Core";
		meta.modId = "SummoningModCore";
		meta.description = "This Mod is Work in Progress.";
		meta.version = "0.1";
	}

	@Override
	public boolean registerBus(EventBus bus, LoadController controller) {
		return true;
	}
}