object Form1: TForm1
  Left = 282
  Top = 243
  Width = 741
  Height = 496
  Caption = 'Form1'
  Color = clBtnFace
  Font.Charset = DEFAULT_CHARSET
  Font.Color = clWindowText
  Font.Height = -11
  Font.Name = 'MS Sans Serif'
  Font.Style = []
  OldCreateOrder = False
  OnCreate = FormCreate
  PixelsPerInch = 96
  TextHeight = 13
  object Memo1: TMemo
    Left = 48
    Top = 88
    Width = 609
    Height = 265
    Lines.Strings = (
      'Memo1')
    ScrollBars = ssBoth
    TabOrder = 0
  end
  object Button1: TButton
    Left = 52
    Top = 384
    Width = 75
    Height = 25
    Caption = 'Button1'
    TabOrder = 1
    OnClick = Button1Click
  end
  object udps: TIdUDPServer
    OnStatus = udpsStatus
    BroadcastEnabled = True
    Bindings = <
      item
        IP = '0.0.0.0'
        Port = 20420
      end>
    DefaultPort = 20420
    OnUDPRead = udpsUDPRead
    ThreadedEvent = True
    Left = 56
    Top = 16
  end
  object Timer1: TTimer
    Interval = 3000
    OnTimer = Timer1Timer
    Left = 184
    Top = 16
  end
end
